package com.qati.cats.ui.fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qati.cats.R;
import com.qati.cats.Utils;
import com.qati.cats.data.models.Cat;
import com.qati.cats.ui.activity.MainActivity;
import com.qati.cats.ui.adapter.CatsRVAdapter;
import com.qati.cats.ui.adapter.VerticalSpaceItemDecoration;
import com.qati.cats.view.CatListView;
import com.qati.cats.view.LikeCatView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.DOWNLOAD_SERVICE;

public abstract class BaseCatsFragment extends MvpFragment implements CatListView, LikeCatView {

    private static final String TAG = "BaseCatsFragment";
    private static final String DOWNLOAD_URL = "down_url";

    @BindView(R.id.rv_cats) RecyclerView rvCats;
    @BindView(R.id.progress_bar) View progressBar;
    @BindView(R.id.tv_empty) View tvEmpty;
    @BindView(R.id.swipe_container) SwipeRefreshLayout refreshLayout;

    Unbinder unbinder;
    CatsRVAdapter adapter;
    String downloadUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = initCatRVAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cats_list, container, false);
        unbinder = ButterKnife.bind(this, v);
        init();
        return v;
    }

    public void init() {
        initRV();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).subscribeToLikeEvent(this, true);
        }
    }

    private void initRV() {
        rvCats.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCats.addItemDecoration(new VerticalSpaceItemDecoration(Utils.dpToPx(getContext(), 8)));
        rvCats.setAdapter(adapter);
    }

    @Override
    public void onCatsLoaded(List<Cat> cats) {
        tvEmpty.setVisibility(cats.isEmpty() ? View.VISIBLE : View.GONE);
        adapter.refresh(cats);
    }

    @Override
    public void updateLikedUI(Cat cat, boolean isLiked) {
        adapter.updateLikedUI(cat, isLiked);
        tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private CatsRVAdapter initCatRVAdapter() {
        return new CatsRVAdapter() {
            @Override
            public void onLikedClicked(Cat cat, boolean isChecked, int pos) {
                processLikeClick(cat, isChecked, pos);
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).notifyOtherFragments(cat, isChecked, getCatListType());
                }
            }

            @Override
            public void onDownloadClicked(Cat cat, int pos) {
                downloadUrl = cat.url;
                if (isStoragePermissionGranted()) {
                    saveToDownloads(getContext(), cat.url);
                }
            }
        };
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (downloadUrl != null) {
                saveToDownloads(getContext(), downloadUrl);
            }
        }
    }

    @Override
    public void onError(Throwable error) {
        error.printStackTrace();
        showErrorMessage(error.getMessage());
    }

    @Override
    public void onLoadingEnable(boolean isEnable) {
        progressBar.setVisibility(isEnable && !refreshLayout.isRefreshing() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).subscribeToLikeEvent(this, false);
        }
    }

    private void saveToDownloads(Context c, String uri) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "cat_photo");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm = (DownloadManager) c.getSystemService(DOWNLOAD_SERVICE);
        if (dm != null) {
            dm.enqueue(request);
        }
        downloadUrl = null;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        downloadUrl = savedInstanceState != null ? savedInstanceState.getString(DOWNLOAD_URL) : null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DOWNLOAD_URL, downloadUrl);
    }
}

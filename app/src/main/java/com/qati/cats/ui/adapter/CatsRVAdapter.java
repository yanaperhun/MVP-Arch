package com.qati.cats.ui.adapter;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.qati.cats.R;
import com.qati.cats.data.models.Cat;
import com.qati.cats.ui.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class CatsRVAdapter extends RecyclerView.Adapter<CatsRVAdapter.CatVH> {

    private List<Cat> cats = new ArrayList<>();

    @NonNull
    @Override
    public CatVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CatVH(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cat, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatVH catVH, int i) {
        int index = catVH.getAdapterPosition();

        catVH.btnLike.setOnCheckedChangeListener(null);
        catVH.btnLike.setChecked(cats.get(i).isFavourite());

        catVH.btnLike.setOnCheckedChangeListener((buttonView, isChecked) -> {

            onLikedClicked(cats.get(index), isChecked, index);
            notifyDataSetChanged();
        });

        catVH.btnDownload.setOnClickListener(v -> onDownloadClicked(cats.get(index), index));

        GlideApp.with(catVH.imageView.getContext())
                .load(cats.get(i).getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.printStackTrace();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        new Handler().post(() -> {
                            catVH.btnDownload.setVisibility(View.VISIBLE);
                            catVH.btnLike.setVisibility(View.VISIBLE);
                        });
                        return false;
                    }
                })
                .into(catVH.imageView);
    }


    public void updateLikedUI(Cat cat, boolean isLiked) {
        int index = cats.indexOf(cat);
        if (index != -1) {
            notifyItemChanged(index);
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public void refresh(List<Cat> cats) {
        this.cats = cats;
        notifyDataSetChanged();
    }

    public abstract void onLikedClicked(Cat cat, boolean isChecked, int pos);

    public abstract void onDownloadClicked(Cat cat, int pos);

    static class CatVH extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView) ImageView imageView;
        @BindView(R.id.btnLike) ToggleButton btnLike;
        @BindView(R.id.btnDownload) View btnDownload;

        CatVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}

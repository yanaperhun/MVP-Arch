package com.qati.cats.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qati.cats.R;
import com.qati.cats.data.models.Cat;
import com.qati.cats.ui.adapter.VPAdapter;
import com.qati.cats.view.CatListView;
import com.qati.cats.view.LikeCatView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.view_pager) ViewPager viewPager;
    Unbinder unbinder;

    ArrayList<LikeCatView> catFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initViewPager();

    }

    private void initViewPager() {
        catFragment = new ArrayList<>();
        viewPager.setAdapter(new VPAdapter(getSupportFragmentManager(),
                new String[]{getString(R.string.all), getString(R.string.favourites)}));
        tabs.setupWithViewPager(viewPager);
    }

    public void notifyOtherFragments(Cat cat, boolean isLiked, int eventFragment) {
        for (LikeCatView likeCatView : catFragment) {
            runOnUiThread(() -> likeCatView.updateLikedUI(cat, isLiked));

        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    public void subscribeToLikeEvent(LikeCatView fragment, boolean isSubscribe) {
        if (isSubscribe) {
            for (LikeCatView listView : catFragment) {
                if (listView.getCatListType() == fragment.getCatListType()) {
                    return;
                }
            }
            catFragment.add(fragment);
        } else {
            for (LikeCatView listView : catFragment) {
                if (listView.getCatListType() == fragment.getCatListType()) {
                    catFragment.remove(listView);
                    break;
                }
            }
        }
    }
}

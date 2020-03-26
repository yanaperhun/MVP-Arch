package com.qati.cats.ui.fragment;

import android.support.v4.app.Fragment;

public class VPFragmentsFactory {

    public static Fragment getForPosition(int i) {
        switch (i) {
            case 0:
                return new AllCatsFragment();
            case 1:
                return new FavouriteCatsFragment();
        }
        return new Fragment();
    }
}

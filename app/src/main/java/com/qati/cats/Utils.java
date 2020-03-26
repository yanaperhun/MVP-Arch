package com.qati.cats;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

public class Utils {
    public static int dpToPx(@Nullable Context c, int dp) {
        if (c == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}

package com.folioreader.ui.activity;

import android.app.Activity;


@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class FolioActivityHolder {

    private static Activity activity;


    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity newActivity) {
        activity = newActivity;
    }
}

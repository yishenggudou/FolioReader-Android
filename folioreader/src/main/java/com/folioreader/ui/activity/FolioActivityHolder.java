package com.folioreader.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.folioreader.Config;
import com.folioreader.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;
import java.util.List;


@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class FolioActivityHolder {

    private Context context;
    private static AdView adView;
    private static AdRequest adRequest;
    private static Integer viewTimes = 0;
    private static Integer requestTimes = 0;
    private static Integer initMobileAdsTimes = 0;
    private String LOG_TAG = "[folio][admob]";
    private static String admobUnitId;
    private static String admobApplicationId;
    private static List<String> testDeviceList;
    private Activity activity;

    public static Integer getInitMobileAdsTimes() {
        return initMobileAdsTimes;
    }

    public static void setInitMobileAdsTimes(Integer initMobileAdsTimes) {
        FolioActivityHolder.initMobileAdsTimes = initMobileAdsTimes;
    }

    public static Integer getRequestTimes() {
        return requestTimes;
    }

    public static void setRequestTimes(Integer requestTimes) {
        FolioActivityHolder.requestTimes = requestTimes;
    }

    public static AdView getAdView() {
        return FolioActivityHolder.adView;
    }

    public static AdRequest getAdRequest() {
        return adRequest;
    }

    public static void setAdRequest(AdRequest adRequest) {
        FolioActivityHolder.adRequest = adRequest;
        String LOG_TAG = "[folio][admob]";
        Log.v(LOG_TAG, "[request][set] [times]=" + viewTimes);

    }

    public static void setAdView(AdView adView) {
        FolioActivityHolder.adView = adView;
        FolioActivityHolder.viewTimes += 1;
        String LOG_TAG = "[folio][admob]";
        Log.v(LOG_TAG, "[view][set] [times]=" + viewTimes);
    }

    public static List<String> getTestDeviceList() {
        return testDeviceList;
    }

    public static void setTestDeviceList(List<String> testDeviceList) {
        FolioActivityHolder.testDeviceList = testDeviceList;
    }

    public static String getAdmobUnitId() {
        return admobUnitId;
    }

    public static void setAdmobUnitId(String admobUnitId) {
        FolioActivityHolder.admobUnitId = admobUnitId;
    }

    public static String getAdmobApplicationId() {
        return admobApplicationId;
    }

    public static void setAdmobApplicationId(String admobApplicationId) {
        FolioActivityHolder.admobApplicationId = admobApplicationId;
    }

    public FolioActivityHolder(Context context, Activity activity, ConstraintLayout layout) {
        this.activity = activity;
        this.context = context;
        if (getAdView() != null) {
           return;
        }
        AdView adView = new AdView(activity);
        AdRequest adRequest = getAdRequest();
        if (adRequest == null) {
            adRequest = new AdRequest.Builder().build();
            setAdRequest(adRequest);
        }
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        String unitId = getAdmobUnitId();
        AdmobAdListener adListener = new AdmobAdListener(adView, adRequest);
        adView.setAdListener(adListener);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setId(View.generateViewId());
        adView.setLayoutParams(params);
        adView.setAdUnitId(unitId);
        layout.addView(adView);
        ConstraintSet cs = new ConstraintSet();

        // Create ConstraintSet
        cs.clone(layout);

        // Bottom of the banner to bottom of parent, 16dp
        cs.connect(adView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        // Center it horizontally with the parent.
        cs.centerHorizontally(adView.getId(), ConstraintSet.PARENT_ID);
        // Set horizontal bias
        cs.setHorizontalBias(adView.getId(),0.5f);
        // Since the height is already set, these constraints would be enough to
        // keep the banner to the bottom of the page. Apply the constraints to the layout
        cs.applyTo(layout);

        setAdView(adView);
        Log.v(LOG_TAG, "[unitId]=" + unitId + " [application]=" + getAdmobApplicationId());
    }

    @SuppressLint("MissingPermission")
    public void forceLoadAd(AdView adView, AdRequest adRequest) {
        adView.loadAd(adRequest);
        FolioActivityHolder.requestTimes += 1;
        Log.v(LOG_TAG, "[request][times]" + getRequestTimes());
    }

    public void initAdmob() {


        List<String> testDeviceIds = Arrays.asList("15907AE3104A03E37300C3E6EB3207D3", "6DD01961975554F5E6CA0C3C7E2CB4FA", AdRequest.DEVICE_ID_EMULATOR);
        if (getTestDeviceList() != null) {
            testDeviceIds = getTestDeviceList();
        }
        Log.v(LOG_TAG, "[testDeviceIds]=" + testDeviceIds);

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                FolioActivityHolder.initMobileAdsTimes += 1;
                Log.v(LOG_TAG, "[complete][MobileAds] [times]=" + getInitMobileAdsTimes());

            }
        });


        /**
         * https://developers.google.com/admob/android/rel-notes
         */


        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        Log.v(LOG_TAG, "[is-test]" + adRequest.isTestDevice(context));
        forceLoadAd(adView, adRequest);
        Log.v(LOG_TAG, "[init-load]");
    }
}

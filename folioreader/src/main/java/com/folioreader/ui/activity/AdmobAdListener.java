package com.folioreader.ui.activity;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class AdmobAdListener extends AdListener {

    private String LOG_TAG = "[admob][folio][listener]";

    private Integer loadTimes = 1;
    private Integer failTimes = 0;
    private AdRequest adRequest;
    private AdView adView;

    public Integer getLoadTimes() {
        return loadTimes;
    }

    public Integer getFailTimes() {
        return failTimes;
    }

    public AdmobAdListener(AdView adView, AdRequest adRequest) {
        this.adRequest = adRequest;
        this.adView = adView;
    }

    @Override
    public void onAdLoaded() {
        Log.v(LOG_TAG, "[loaded][adView][finished][times]=" + loadTimes + " [failTimes]=" + failTimes);
        loadTimes += 1;
        super.onAdLoaded();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onAdFailedToLoad(LoadAdError loadAdError) {
        // Code to be executed when an ad request fails.
        Log.v(LOG_TAG, "[fail][adView][times]" + loadTimes + " [onAdFailedToLoad]" + loadAdError.getCode() + "   [failTimes]=" + failTimes);
        Log.v(LOG_TAG, "[fail][adView][error]" + loadAdError.getMessage());
        Log.v(LOG_TAG, "[fail][adView][domain]" + loadAdError.getDomain());
        if (loadTimes < 20) {
            adView.loadAd(adRequest);
            failTimes += 1;
        }
    }

    @Override
    public void onAdOpened() {
        Log.v(LOG_TAG, "[opened][adView]:onAdOpened");

    }

    @Override
    public void onAdClicked() {
        Log.v(LOG_TAG, "[clicked][adView]onAdClicked");

    }



    @Override
    public void onAdLeftApplication() {
        Log.v(LOG_TAG, "[adView]:onAdLeftApplication");

    }

    @Override
    public void onAdClosed() {
        Log.v(LOG_TAG, "[adView]:onAdClosed");

    }
}

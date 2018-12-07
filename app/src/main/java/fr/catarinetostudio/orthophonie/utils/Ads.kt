package fr.catarinetostudio.orthophonie.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import fr.catarinetostudio.orthophonie.R

class Ads (ac: Activity) {

    private var mAdView : AdView

    init {
        MobileAds.initialize(ac, ac.getString(R.string.ad_app_id))
        mAdView = ac.findViewById(R.id.adViewBottom)
        val adRequestBottom = AdRequest.Builder().build()
        mAdView.loadAd(adRequestBottom)
    }
}
package fr.insa_cvl.orthophonie

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class Ads {

    var mAdView : AdView

    constructor(ac : Activity) {
        MobileAds.initialize(ac, ac.getString(R.string.ad_app_id))

        mAdView = ac.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}
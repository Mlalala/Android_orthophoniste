package fr.insa_cvl.orthophonie

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class Ads {

    var mAdView : AdView

    constructor(ac : Activity) {
        MobileAds.initialize(ac, "ca-app-pub-1209988960443824~4847019926")

        mAdView = ac.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}
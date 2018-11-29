package fr.catarinetostudio.orthophonie

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class Ads {

    var mAdView : AdView

    constructor(ac : Activity) {
        MobileAds.initialize(ac, ac.getString(R.string.ad_app_id))

        mAdView = ac.findViewById(R.id.adViewTop)
        val adRequestTop = AdRequest.Builder().build()
        mAdView.loadAd(adRequestTop)

        mAdView = ac.findViewById(R.id.adViewBottom)
        val adRequestBottom = AdRequest.Builder().build()
        mAdView.loadAd(adRequestBottom)
    }
}
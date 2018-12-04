package fr.catarinetostudio.orthophonie.utils

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import fr.catarinetostudio.orthophonie.R

class Ads {

    private var mAdView : AdView

    constructor(ac : Activity) {
        MobileAds.initialize(ac, ac.getString(R.string.ad_app_id))

        /*
        mAdView = ac.findViewById(R.id.adViewTop)
        val adRequestTop = AdRequest.Builder().build()
        mAdView.loadAd(adRequestTop)
        */

        mAdView = ac.findViewById(R.id.adViewBottom)
        val adRequestBottom = AdRequest.Builder().build()
        mAdView.loadAd(adRequestBottom)
    }
}
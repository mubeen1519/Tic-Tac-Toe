package com.honeycake.tictactoe.ui.composable

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.ads.MaxRewardedAd
import com.applovin.sdk.AppLovinSdk
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import com.facebook.ads.RewardedVideoAd
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.honeycake.tictactoe.R


@Composable
fun AdmobBannerAds() {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        factory = { context ->
            // on below line specifying ad view.
            com.google.android.gms.ads.AdView(context).apply {
                // on below line specifying ad size
                //adSize = AdSize.BANNER
                // on below line specifying ad unit id
                // currently added a test ad unit id.
                setAdSize(com.google.android.gms.ads.AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/6300978111"
                // calling load ad to load our ad.
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
@Composable
fun AdmobInterstitialAds( context : Context) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        var interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd? = null

        fun loadInterstitialAd(context: Context){
            var adRequest = AdRequest.Builder().build()
            var adUnitId = "ca-app-pub-3940256099942544/1033173712"
            com.google.android.gms.ads.interstitial.InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    interstitialAd = null

                }

                override fun onAdLoaded(p0: com.google.android.gms.ads.interstitial.InterstitialAd) {
                    super.onAdLoaded(p0)

                    interstitialAd = p0
                }
            })
        }


        if (interstitialAd != null){
            interstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdFailedToShowFullScreenContent(p0: com.google.android.gms.ads.AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    interstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    interstitialAd = null
                    loadInterstitialAd(context)
                }
            }
        }
        interstitialAd?.show(context as Activity)



    }

}

@Composable
fun AdmobRewardAds(context: Context) {

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){

        var rewardedAd : RewardedAd?=null

        fun loadRewardAd(context: Context){

            RewardedAd.load(context,"ca-app-pub-3940256099942544/5224354917",AdRequest.Builder().build(),object :
                RewardedAdLoadCallback() {

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    rewardedAd = null
                }

                override fun onAdLoaded(p0: RewardedAd) {
                    super.onAdLoaded(p0)
                    rewardedAd = p0
                }


                })

        }

             if (rewardedAd != null)
             {
               rewardedAd!!.show(context as Activity, OnUserEarnedRewardListener {
                   Toast.makeText(context, "Rewarded!", Toast.LENGTH_SHORT).show()
                   loadRewardAd(context)
               })
             }


    }

}

@Composable
fun FacebookBannerAds() {

    AndroidView(
        factory = { ctx ->
            AdView(ctx, "YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
        },
        update = { adView ->
            // Load ad or perform other ad-related operations here
            adView.loadAd()
        }
    )

}

@Composable
fun FacebookInterstitialAd(context: Context) {

        val interstitialAd = InterstitialAd(context, "YOUR_PLACEMENT_ID")
        interstitialAd.loadAd()

        var config = interstitialAd.buildLoadAdConfig().withAdListener(object : InterstitialAdListener{
            override fun onError(p0: Ad?, p1: AdError?) {
                if (p1 != null) {
                    Toast.makeText(context, "interstitial ${p1.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAdLoaded(p0: Ad?) {
                Toast.makeText(context, "Ads Loaded", Toast.LENGTH_SHORT).show()
                interstitialAd.show()
            }

            override fun onAdClicked(p0: Ad?) {
                Toast.makeText(context, "Ads Clicked", Toast.LENGTH_SHORT).show()
            }

            override fun onLoggingImpression(p0: Ad?) {

            }

            override fun onInterstitialDisplayed(p0: Ad?) {
                Toast.makeText(context, "Ads Displayed", Toast.LENGTH_SHORT).show()
            }

            override fun onInterstitialDismissed(p0: Ad?) {

            }
        }).build()

    interstitialAd.loadAd(config)

        if (interstitialAd !=null){
            if (interstitialAd.isAdLoaded){
                interstitialAd.show()
            }else{
                interstitialAd.loadAd()
            }
        }


    }

@Composable
fun FacebookRewardAds(context: Context) {

    val rewardedAd = RewardedVideoAd(context, "YOUR_PLACEMENT_ID")
    rewardedAd.loadAd(rewardedAd.buildLoadAdConfig().build())
    rewardedAd.show()
}


@Composable
fun ApplovinBannerAds() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // MAX banner ad
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = dimensionResource(R.dimen.banner_height))
                .align(alignment = Alignment.BottomCenter),
            factory = { context ->
                MaxAdView("YOUR_AD_UNIT_ID", context).apply {
                    visibility = View.VISIBLE
                    loadAd()
                    startAutoRefresh()
                }
            }
        )
    }
}

@Composable
fun AppLovinInterstitialAd() {

    var activity :Activity?=null
    var interstitialAd: MaxInterstitialAd? = null
    //var retryAttempt = 0.0
     var maxAdListener : MaxAdListener? = null
    // Set up the layout for the interstitial ad
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Load and show the interstitial ad
        DisposableEffect(activity) {
            interstitialAd = MaxInterstitialAd("your id",activity)
            interstitialAd?.loadAd()
            interstitialAd?.showAd()
            onDispose {
                // Dispose resources if needed
                interstitialAd?.destroy()
            }
        }
    }

     interstitialAd?.setListener(maxAdListener)


    if  (interstitialAd!!.isReady)
    {
        interstitialAd?.showAd()
    }

}


@Composable
fun AppLovinRewardAds() {
    var activity : Activity?=null
    lateinit var rewardedAd: MaxRewardedAd
    //var rewardedAd :RewardedAd?=null
    var appLovinSdk : AppLovinSdk?=null
//    var rewardedAd = remember {
//        MaxRewardedAd("YOUR_APPLOVIN_REWARDED_AD_UNIT_ID", appLovinSdk)

// Set up the layout for the rewarded ad
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Load and show the rewarded ad
        DisposableEffect(activity) {

           rewardedAd = MaxRewardedAd.getInstance("your id", activity)
            rewardedAd.loadAd()

            rewardedAd.showAd()
            onDispose {
                // Dispose resources if needed
                rewardedAd.destroy()
            }


        }
        if ( rewardedAd.isReady)
        {
            rewardedAd.showAd();
        }
    }



}




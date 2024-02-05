package com.honeycake.tictactoe.ui

import android.content.Context
import androidx.compose.runtime.Composable
import com.honeycake.tictactoe.ui.composable.AdmobBannerAds
import com.honeycake.tictactoe.ui.composable.AdmobInterstitial
import com.honeycake.tictactoe.ui.composable.AdmobInterstitialAds
import com.honeycake.tictactoe.ui.composable.AdmobRewardAds
import com.honeycake.tictactoe.ui.composable.AppLovinInterstitialAd
import com.honeycake.tictactoe.ui.composable.AppLovinRewardAds
import com.honeycake.tictactoe.ui.composable.ApplovinBannerAds
import com.honeycake.tictactoe.ui.composable.FacebookBannerAds
import com.honeycake.tictactoe.ui.composable.FacebookInterstitialAd
import com.honeycake.tictactoe.ui.composable.FacebookRewardAds

// AdNetwork enum class to represent different ad networks
enum class AdNetwork {
    FACEBOOK,
    APPLOVIN,
    ADMOB
}

// AdManager class to manage different ad network implementations
class AdManager private constructor() {
    companion object {
        private var instance: AdManager? = null

        fun getInstance(): AdManager {
            if (instance == null) {
                instance = AdManager()
            }
            return instance as AdManager
        }
    }

    // Function to show banner ad based on the selected ad network

    @Composable
    fun showBannerAd(adNetwork: AdNetwork) {
        when (adNetwork) {
            AdNetwork.FACEBOOK -> {
                // Implement Facebook banner ad logic
                // FacebookBannerAd.show()
                FacebookBannerAds()
            }

            AdNetwork.APPLOVIN -> {
                // Implement AppLovin banner ad logic
                // AppLovinBannerAd.show()

                ApplovinBannerAds()
            }

            AdNetwork.ADMOB -> {
                // Implement AdMob banner ad logic
                // AdMobBannerAd.show()
                AdmobBannerAds()
            }
        }
    }

    // Function to show interstitial ad based on the selected ad network

    @Composable
    fun showInterstitialAd(adNetwork: AdNetwork, context: Context) {

        when (adNetwork) {
            AdNetwork.FACEBOOK -> {
                // Implement Facebook interstitial ad logic
                // FacebookInterstitialAd.show()

                FacebookInterstitialAd(context)
            }

            AdNetwork.APPLOVIN -> {
                // Implement AppLovin interstitial ad logic
                // AppLovinInterstitialAd.show()

                AppLovinInterstitialAd()
            }

            AdNetwork.ADMOB -> {
                // Implement AdMob interstitial ad logic
                // AdMobInterstitialAd.show()

              //  AdmobInterstitialAds(context)
                AdmobInterstitial()
            }
        }
    }

    // Function to show rewarded ad based on the selected ad network
    @Composable
    fun showRewardedAd(adNetwork: AdNetwork, context: Context) {
        when (adNetwork) {
            AdNetwork.FACEBOOK -> {
                // Implement Facebook rewarded ad logic
                // FacebookRewardedAd.show()
                FacebookRewardAds(context)
            }

            AdNetwork.APPLOVIN -> {
                // Implement AppLovin rewarded ad logic
                // AppLovinRewardedAd.show()

                AppLovinRewardAds()
            }

            AdNetwork.ADMOB -> {
                // Implement AdMob rewarded ad logic
                // AdMobRewardedAd.show()

                AdmobRewardAds(context)


            }
        }
    }
}
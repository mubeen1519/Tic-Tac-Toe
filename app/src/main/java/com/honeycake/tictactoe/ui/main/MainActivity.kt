package com.honeycake.tictactoe.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.honeycake.tictactoe.ui.TicTacToeApp
import com.honeycake.tictactoe.ui.composable.rememberImeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       version()


        //FirebaseAnalytic
        analytics = FirebaseAnalytics.getInstance(this)

        //Firebase Messaging
        FirebaseMessaging.getInstance().subscribeToTopic("notification")


        AudienceNetworkAds.initialize(this)
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.getInstance(this).initializeSdk { configuration: AppLovinSdkConfiguration ->
            // AppLovin SDK is initialized, start loading ads
        }

        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,

            )
        setContent {

            MobileAds.initialize(this)


            TicTacToeApp()

        }


    }

    //check version
    fun version() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            var permissionNotify =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            if (permissionNotify != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }
}

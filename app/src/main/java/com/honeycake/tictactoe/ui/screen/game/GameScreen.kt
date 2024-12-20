package com.honeycake.tictactoe.ui.screen.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.honeycake.tictactoe.R
import com.honeycake.tictactoe.ui.LocalNavigationProvider
import com.honeycake.tictactoe.ui.composable.IconBack
import com.honeycake.tictactoe.ui.screen.game.composable.CustomDialog
import com.honeycake.tictactoe.ui.screen.game.composable.GameBoard
import com.honeycake.tictactoe.ui.screen.game.composable.PlayersInfo
import com.honeycake.tictactoe.ui.screen.home.navigateToHome
import com.honeycake.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun GameScreen(gameViewModel: GameViewModel = hiltViewModel()) {
    val gameUiState by gameViewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    GameContent(
        gameUiState,
        onButtonClicked = gameViewModel::onButtonClick,
        onClickBackButton = { navController.navigateToHome() },
        navigateToHome = { navController.navigateToHome() }
    )

}

@Composable
private fun GameContent(
    gameUiState: GameUiState,
    onButtonClicked: (Int) -> Unit,
    onClickBackButton: () -> Unit,
    navigateToHome: () -> Unit
) {

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.backgrouda),
                    contentScale = ContentScale.Crop
                )
        ) {
            IconBack(onClick = onClickBackButton)
            PlayersInfo(gameUiState)
            GameBoard(gameUiState, onButtonClicked = onButtonClicked)
        }
        if (gameUiState.gameResult != GameResult.IDEAL) {
            CustomDialog(
                openDialogCustom = remember { mutableStateOf(false) },
                onClickButton = navigateToHome,
                gameResult = gameUiState.gameResult
            )

        }
    }
}


@Composable
fun InterstitialAdComponent(context: Context) {
    var interstitialAdState by remember { mutableStateOf<InterstitialAd?>(null) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (interstitialAdState == null) {
            // Load the interstitial ad if it's not already loaded
            loadInterstitialAd(context) { loadedInterstitialAd ->
                interstitialAdState = loadedInterstitialAd
                // Optionally, you can choose to show the ad immediately after loading
                loadedInterstitialAd?.show(context as Activity)
            }
        } else {
            // The interstitial ad is loaded, show it
            interstitialAdState!!.show(context as Activity)
        }
    }
}

fun loadInterstitialAd(context: Context, onAdLoaded: (InterstitialAd?) -> Unit) {
    val adRequest = AdRequest.Builder().build()
    val adUnitId = "ca-app-pub-3940256099942544/1033173712"

    InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(p0: LoadAdError) {
            super.onAdFailedToLoad(p0)
            // Handle ad loading failure, you might want to log or show a message
            onAdLoaded(null)
        }

        override fun onAdLoaded(p0: InterstitialAd) {
            super.onAdLoaded(p0)
            // Ad is loaded, call the callback with the interstitialAd
            onAdLoaded(p0)
        }
    })
}


@Preview(showBackground = true)
@Composable
fun PreviewGame(){
    TicTacToeTheme {
        GameContent(gameUiState = GameUiState(), onButtonClicked = {}, onClickBackButton = { /*TODO*/ }) {

        }
    }
}
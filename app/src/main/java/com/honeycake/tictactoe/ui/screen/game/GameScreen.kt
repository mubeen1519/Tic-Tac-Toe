package com.honeycake.tictactoe.ui.screen.game


import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.honeycake.tictactoe.R
import com.honeycake.tictactoe.ui.AdManager
import com.honeycake.tictactoe.ui.AdNetwork
import com.honeycake.tictactoe.ui.LocalNavigationProvider
import com.honeycake.tictactoe.ui.composable.IconBack
import com.honeycake.tictactoe.ui.composable.dialogPrompt
import com.honeycake.tictactoe.ui.screen.game.composable.CustomDialog
import com.honeycake.tictactoe.ui.screen.game.composable.GameBoard
import com.honeycake.tictactoe.ui.screen.game.composable.PlayersInfo
import com.honeycake.tictactoe.ui.screen.home.navigateToHome
import com.honeycake.tictactoe.ui.screen.load_game.LoadViewModel
import com.honeycake.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun GameScreen(gameViewModel: GameViewModel = hiltViewModel(),viewModel: LoadViewModel = hiltViewModel()) {
    val gameUiState by gameViewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    //make a variable for dialog
    var showDialog by remember{ mutableStateOf(false) }
    //This variable refers to the UI of the app
    val state by viewModel.state.collectAsState()






    GameContent(
        gameUiState,
        onButtonClicked = gameViewModel::onButtonClick,
        onClickBackButton = { showDialog = true },
        navigateToHome = { navController.navigateToHome() }
    )
    if (showDialog){
        dialogPrompt(title = "Conformation", message = "Are You Sure to Back the Game", navigateToHome = { navController.navigateToHome() }) {
            navController.navigateToGame(state.gameId,1)
        }
    }

}

@Composable
private fun GameContent(
    gameUiState: GameUiState,
    onButtonClicked: (Int) -> Unit,
    onClickBackButton: () -> Unit,
    navigateToHome: () -> Unit
) {

    val adManager: AdManager = remember { AdManager.getInstance() }
    var showInterstitialAd by remember { mutableStateOf(false) }
//    val navController = LocalNavigationProvider.current
//    var showDialog by remember{ mutableStateOf(false) }


    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.backgrouda),
                    contentScale = ContentScale.Crop
                )
        ) {
            IconBack(onClick = {
//                showDialog = true
                onClickBackButton.invoke()
            })
            PlayersInfo(gameUiState)
            GameBoard(gameUiState, onButtonClicked = onButtonClicked)
        }
        if (gameUiState.gameResult != GameResult.IDEAL) {
            CustomDialog(
                openDialogCustom = remember { mutableStateOf(false) },
                onClickButton = {
                    showInterstitialAd = true
                    navigateToHome()
                                },
                gameResult = gameUiState.gameResult
            )

        }
    }
    if (showInterstitialAd){
        adManager.showInterstitialAd(adNetwork = AdNetwork.ADMOB, context = LocalContext.current,navigateToHome)
        showInterstitialAd=false
    }
//    if (showDialog){
//        dialogPrompt(title = "Conformation", message = "Are You Sure to Back the Game", navigateToHome = { navigateToHome }) {
//            navController.navigateToGame("",1)
//        }
    }






@Composable
fun InterstitialAdComponent(context: Context,navigateToHome: () -> Unit) {

    var interstitialAdState by remember { mutableStateOf<InterstitialAd?>(null) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (interstitialAdState == null) {
            // Load the interstitial ad if it's not already loaded
            loadInterstitialAd(context,navigateToHome) { loadedInterstitialAd ->
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

fun loadInterstitialAd(context: Context,navigateToHome: () -> Unit, onAdLoaded: (InterstitialAd?) -> Unit) {

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
            navigateToHome
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


package com.honeycake.tictactoe.ui.screen.create_game

import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.PreviewActivity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.honeycake.tictactoe.R
import com.honeycake.tictactoe.ui.AdManager
import com.honeycake.tictactoe.ui.AdNetwork
import com.honeycake.tictactoe.ui.LocalNavigationProvider
import com.honeycake.tictactoe.ui.composable.ButtonItem
import com.honeycake.tictactoe.ui.composable.EditTextFiled
import com.honeycake.tictactoe.ui.composable.TicTacToeScaffold
import com.honeycake.tictactoe.ui.composable.rememberImeState
import com.honeycake.tictactoe.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun CreateGameScreen(
    viewModel: CreateGameViewModel = hiltViewModel(),
) {
    val navController = LocalNavigationProvider.current
    val state by viewModel.state.collectAsState()
    val adManager = AdManager.getInstance()


//    var imeState = rememberImeState()
//    var scrollState = rememberScrollState()
//
//    LaunchedEffect(key1 = imeState.value){
//        if (imeState.value){
//            scrollState.animateScrollTo(scrollState.maxValue, tween(3000))
//        }
//    }
    CreateGameContent(
        state = state,
        onChangePlayerName = viewModel::onChangePlayerName,
        onClickCreateGame = { viewModel.onCreateGameClicked(navController) }
    )
    adManager.showBannerAd(adNetwork = AdNetwork.ADMOB )
   // AdmobBanner()
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateGameContent(
    state: CreateGameUiState,
    onChangePlayerName: (String) -> Unit,
    onClickCreateGame: () -> Unit,
) {


    val coroutine = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()

    TicTacToeScaffold {
        EditTextFiled(
            text = state.firstPlayerName,
            hint = stringResource(R.string.enter_your_name),
            placeHolder = "Ex: John",
            modifier = Modifier.imePadding().navigationBarsWithImePadding().onFocusEvent
            { event->

               if (event.isFocused){

                   GlobalScope.launch{
                     bringIntoViewRequester.bringIntoView()
                   }
               }

            },
            onChange = onChangePlayerName,
            textStyle = Typography.titleSmall,
        )
        ButtonItem(
            modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
            text = stringResource(R.string.create_game),
            isEnabled = state.isButtonEnabled,
            onClick = onClickCreateGame
        )
    }




}


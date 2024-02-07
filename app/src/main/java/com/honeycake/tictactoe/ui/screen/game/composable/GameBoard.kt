package com.honeycake.tictactoe.ui.screen.game.composable

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.honeycake.tictactoe.R
import com.honeycake.tictactoe.ui.screen.game.GameUiState

@Composable
fun GameBoard(
    state: GameUiState,
    onButtonClicked: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(5))
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Log.e("Sara",state.board.toString())
        Column(
            modifier = Modifier

                .fillMaxSize().background(Color.White, shape = RoundedCornerShape(16.dp)).alpha(0.6f),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            for (row in 0..2) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .aspectRatio(1f)
                ) {
                    for (col in 0..2) {
                        val index = row * 3 + col
                        val image =when(state.board[index]){
                            0 -> null
                            1 -> R.drawable.x
                            else -> R.drawable.o

                        }

                        GameCell(
                            onButtonClicked = {
                                Log.e("TAG", "GameCell:${index} ")
                                onButtonClicked(index)
                            },
                            imageResource = image,
                            isEnabled = state.enabled
                        )
                    }
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.icon_game_structure),
            contentDescription = "image structure",
        )
    }
}

@Preview
@Composable
fun PreviewGameBoard() {
    GameBoard(state = GameUiState(), onButtonClicked = {})
}


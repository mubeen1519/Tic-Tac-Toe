package com.honeycake.tictactoe.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun dialogPrompt(
    title: String,
    message: String,
    navigateToHome: () -> Unit,
    resumeGame: () -> Unit
) {

    Dialog(
        onDismissRequest = {
            navigateToHome()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )

    ) {

        Card(
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(200.dp),
            shape = RoundedCornerShape(15.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF118CEC))


            ) {

                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = message,
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {

                    Button(
                        onClick = {
                            navigateToHome.invoke()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF28C2FF)
                        )
                    ) {

                        Text(
                            text = "Home",
                            color = Color.White,
                            style = MaterialTheme.typography.h6,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = {
                            resumeGame.invoke()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(10.dp)
                            .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF28C2FF)
                                )
                    ) {

                        Text(
                            text = "Resume",
                            color = Color.White,
                            style = MaterialTheme.typography.h6,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
        }


    }

}
@Preview(showBackground = true)
@Composable
fun dialogPreview() {

    dialogPrompt(title = "Conformation", message = "Are you Sure to back the Game", navigateToHome = { }) {

    }
}
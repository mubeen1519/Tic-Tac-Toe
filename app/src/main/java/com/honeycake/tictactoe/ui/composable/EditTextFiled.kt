package com.honeycake.tictactoe.ui.composable


import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.honeycake.tictactoe.R
import com.honeycake.tictactoe.ui.theme.Card
import com.honeycake.tictactoe.ui.theme.RoundedShape
import com.honeycake.tictactoe.ui.theme.TextColor
import com.honeycake.tictactoe.ui.theme.Typography
import com.honeycake.tictactoe.ui.theme.White38

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextFiled(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    placeHolder: String = "",
    onChange: (String) -> Unit = {},
    isLeadingIcon: Boolean = false,
    readOnly: Boolean = false,
    onClickLeadingIcon: () -> Unit = {},
    textStyle: TextStyle = Typography.titleSmall
) {

    val foucusmanager = LocalFocusManager.current
    var imeState = rememberImeState()
    var scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value){
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(3000))
        }
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        value = text,
        onValueChange = onChange,
        label = {
            Text(
                text = hint,
                style = Typography.labelSmall,
                color = TextColor
            )
        },
        shape = RoundedShape.medium,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Card,
            unfocusedBorderColor = Card
        ),
        trailingIcon = {
            if (isLeadingIcon) {
                IconButton(onClick = onClickLeadingIcon) {
                    Icon(
                        painter = painterResource(id = R.drawable.copy),
                        contentDescription = "Copy button",
                        tint = Card
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = placeHolder,
                style = textStyle,
                color = White38
            )
        },
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone ={
                foucusmanager.clearFocus()
            }
        )
    )
}

@Preview
@Composable
fun PreviewEditText() {
    EditTextFiled()
}
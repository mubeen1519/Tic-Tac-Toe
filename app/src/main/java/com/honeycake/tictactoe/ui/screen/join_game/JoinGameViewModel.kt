package com.honeycake.tictactoe.ui.screen.join_game

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.honeycake.tictactoe.data.GameSession
import com.honeycake.tictactoe.domain.repository.XORepository
import com.honeycake.tictactoe.ui.base.BaseViewModel
import com.honeycake.tictactoe.ui.screen.create_game.CreateGameViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinGameViewModel @Inject constructor(
    private val XORepository: XORepository,
) : BaseViewModel<JoinGameUiState>(JoinGameUiState()), JoinGameInteractionListener {

    var context : Context?=null

    fun onChangePlayerName(name: String) {
        updateState { it.copy(secondPlayerName = name) }
    }

    fun onChangeGameId(newId: String) {

        // my change
        var currentId = state.value.gameId

        if (newId != currentId){
            updateState { it.copy(gameId = newId, isButtonEnabled = true) }
        }else{
            Toast.makeText(context, "Please Enter new ID...", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onJoinGameClicked() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (state.value.secondPlayerName.isNotEmpty() && state.value.gameId.isNotEmpty()) {
                       updateGameSession()
                   updateState { it.copy(navigate = true) }
                }else if (state.value.gameId == _state.value.gameId){    //my change
                    updateGameSession()
                    updateState { it.copy(navigate = false) }
                }
            }catch (e: Throwable){

                // my change
                Toast.makeText(context, "ID is already exist", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private suspend fun updateGameSession() {
            XORepository.updateGameSession(
                GameSession(
                    _state.value.firstPlayerName,
                    _state.value.secondPlayerName,
                    true,
                    _state.value.gameId
                )
            )
    }


}
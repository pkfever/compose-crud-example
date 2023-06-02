package com.test.myapplication.ui.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.data.entities.ChatGpt
import com.test.myapplication.data.entities.getChatGptObj
import com.test.myapplication.domain.util.Resource
import com.test.myapplication.repository.OpenAIRepository
import com.test.myapplication.ui.login.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val openAIRepository: OpenAIRepository) :
    ViewModel() {

    private var messageList = mutableStateListOf<ChatGpt>()
    private val _messageListFlow = MutableStateFlow(messageList)
    val messageListFlow: StateFlow<List<ChatGpt>> = _messageListFlow

    var chatMessageState by mutableStateOf(TextFieldState())

    fun updateData(text: String) {

        messageList.add(getChatGptObj(text))
        _messageListFlow.value = messageList
        chatMessageState.text = ""
    }

    fun sendMessage(txt: String) {
        viewModelScope.launch {
            try {
                val response = openAIRepository.generateQueryText(txt)
                when (response.status) {

                    Resource.Status.SUCCESS -> {
                        updateData(response.data?.text ?: "")
                    }
                    Resource.Status.ERROR -> {
                        Log.v("message", "something went wrong!")
                    }
                }
            } catch (ex: Exception) {
                Log.v("message", ex.printStackTrace().toString())
            }
        }
    }
}
package com.test.myapplication.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.myapplication.data.entities.ChatGpt

@Composable
fun ChatScreen(chatViewModel: ChatViewModel) {

    val chatList = chatViewModel.messageListFlow.collectAsState()

    BodyContent(chatViewModel, chatList)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BodyContent(chatViewModel: ChatViewModel, chatList: State<List<ChatGpt>>) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(chatList.value) {
                    ChatListItem(it)
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                        thickness = DividerDefaults.Thickness, color = Color.Black
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {

                Column {

                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        val keyboardController = LocalSoftwareKeyboardController.current
                        val focusRequester = remember { FocusRequester() }
                        val chatMessageTypeState = chatViewModel.chatMessageState

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                                .padding(start = 16.dp)
                                .onFocusChanged { focusState ->
                                    chatMessageTypeState.onFocusChange(focusState.isFocused)
                                }
                                .padding(2.dp),
                            value = chatMessageTypeState.text,
                            onValueChange = { chatMessageTypeState.text = it },
                            colors = TextFieldDefaults.colors(
//                                focusedIndicatorColor = Color.Transparent,
//                                unfocusedIndicatorColor = Color.Transparent,
//                                backgroundColor = Color.Transparent,

//                                focusedIndicatorColor = Color.Transparent,
//                                unfocusedIndicatorColor = Color.Transparent,
//                                disabledIndicatorColor = Color.Transparent, // Consider disabled state as well
//                                // For the background color of the text field itself (the input area)
//                                // In M3, this is controlled by 'unfocusedContainerColor' and 'focusedContainerColor' etc.
//                                // If you want the entire TextField to have a transparent background including the container:
//                                unfocusedContainerColor = Color.Transparent,
//                                focusedContainerColor = Color.Transparent,
//                                disabledContainerColor = Color.Transparent,
//                                cursorColor = MaterialTheme.colorScheme.primary
                            ),
                            placeholder = { Text(text = "Add a message") },
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.7f)
                                .padding(start = 10.dp, end = 16.dp)
                                .clip(RoundedCornerShape(20))
                                .background(Color.Blue),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            ClickableText(
                                text = AnnotatedString("Send"),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .focusRequester(focusRequester)
                                    .padding(top = 7.dp, bottom = 7.dp, start = 10.dp, end = 10.dp),
                                style = TextStyle(
                                    textDecoration = TextDecoration.None,
                                    color = Color.White
                                ),
                                onClick = {
                                    keyboardController?.hide()
                                    chatViewModel.updateData(chatMessageTypeState.text)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatListItem(chatGpt: ChatGpt) {
    Text(text = chatGpt.text ?: "", modifier = Modifier.padding(all = 10.dp))
}

@Preview
@Composable
fun PreviewChatScreen() {
    ChatScreen(chatViewModel = hiltViewModel())
}
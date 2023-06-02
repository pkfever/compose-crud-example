package com.test.myapplication.data.remote.datasource

import com.test.myapplication.data.remote.apiservice.OpenAIApiServices
import javax.inject.Inject

class OpenAIDataSource @Inject constructor(private val chatGptApiServices: OpenAIApiServices) {

    suspend fun generateText(queryText: String) = chatGptApiServices.generateText(queryText)

    suspend fun getAiChatHistory(limit: Int, cursor: String?) =
        chatGptApiServices.previousChat(limit, cursor)

    suspend fun generateImage(queryText: String) = chatGptApiServices.generateImage(queryText)
}
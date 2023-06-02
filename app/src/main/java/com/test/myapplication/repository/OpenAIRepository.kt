package com.test.myapplication.repository


import com.test.myapplication.data.entities.ChatGpt
import com.test.myapplication.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface OpenAIRepository {

    suspend fun generateQueryText(queryText: String): Resource<ChatGpt>

//    fun chatHistory(): Flow<PagingData<ChatGpt>>

//    suspend fun previousChatHistory(dataModel: DataModel): Resource<ChatGptResponse>

//    suspend fun generateDalleImage(queryText: String): Resource<List<String>>
}
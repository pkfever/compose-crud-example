package com.test.myapplication.repository

import com.test.myapplication.data.entities.ChatGpt
import com.test.myapplication.data.remote.datasource.OpenAIDataSource
import com.test.myapplication.domain.util.Resource
import javax.inject.Inject

class OpenAIRepositoryImpl @Inject constructor(
    private val chatGptDataSource: OpenAIDataSource
) :

    OpenAIRepository {

//    private val chatGptMapper = ChatGptMapper()

    override suspend fun generateQueryText(queryText: String): Resource<ChatGpt> {
        return try {

            /* val lastObject = userDao.getLastObject()
             val chatObj = chatGptMapper.getChatObj(queryText, lastObject)

             userDao.insertChat(chatObj)*/

            val data = chatGptDataSource.generateText(queryText)
//            data.data?.let {
//                userDao.insertChat(it)
//            }
            Resource.success(data.data)
        } catch (ex: Exception) {
            Resource.error(ex)
        }
    }


//    override suspend fun previousChatHistory(dataModel: DataModel): Resource<ChatGptResponse> {
//        return try {
//            val data = chatGptDataSource.getAiChatHistory(dataModel.limitSize, dataModel.cursor)
//            val mappedResponse = chatGptMapper.getChatGptResponse(data)
//            Resource.success(mappedResponse)
//        } catch (ex: Exception) {
//            Resource.error(ex)
//        }
//    }
}
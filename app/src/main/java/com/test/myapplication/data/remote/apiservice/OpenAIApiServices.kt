package com.test.myapplication.data.remote.apiservice

import com.test.myapplication.data.entities.ChatGpt
import com.test.myapplication.data.response.BaseResponse
import retrofit2.http.*

interface OpenAIApiServices {

    @FormUrlEncoded
    @POST(ApiConstants.ENDPOINTS.API + ApiConstants.ENDPOINTS.GENERATE_TEXT)
    suspend fun generateText(@Field(ApiConstants.Query.TEXT) text: String): BaseResponse<ChatGpt>

    @GET(ApiConstants.ENDPOINTS.API + ApiConstants.ENDPOINTS.PREVIOUS_CHAT)
    suspend fun previousChat(
        @Query("limit") limit: Int,
        @Query("cursor") page: String?
    ): BaseResponse<List<ChatGpt>>

    @FormUrlEncoded
    @POST(ApiConstants.ENDPOINTS.API + ApiConstants.ENDPOINTS.GENERATE_IMAGE)
    suspend fun generateImage(@Field(ApiConstants.Query.TEXT) text: String): BaseResponse<List<ChatGpt>>
}
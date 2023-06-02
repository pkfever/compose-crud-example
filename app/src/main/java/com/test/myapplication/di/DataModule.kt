package com.test.myapplication.di

import com.test.myapplication.data.remote.apiservice.OpenAIApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideChatGptApiServices(retrofit: Retrofit): OpenAIApiServices =
        retrofit.create(OpenAIApiServices::class.java)
}
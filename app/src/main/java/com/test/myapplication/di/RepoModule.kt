package com.test.myapplication.di

import com.test.myapplication.repository.OpenAIRepository
import com.test.myapplication.repository.OpenAIRepositoryImpl
import com.test.myapplication.repository.UserRepository
import com.test.myapplication.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindOpenAiRepository(openAIRepositoryImpl: OpenAIRepositoryImpl): OpenAIRepository
}
package com.test.myapplication.di

import android.content.Context
import com.test.myapplication.data.UserDao
import com.test.myapplication.data.UserDatabase
import com.test.myapplication.repository.UserRepository
import com.test.myapplication.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserDataBase(@ApplicationContext context: Context) =
        UserDatabase.getDatabase(context).userDao()

}
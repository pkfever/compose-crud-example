package com.test.myapplication.di

import android.content.Context
import com.test.myapplication.data.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserDataBase(@ApplicationContext context: Context) =
        UserDatabase.getDatabase(context)

    @Provides
    fun provideUserDao(userDatabase: UserDatabase) = userDatabase.userDao()

}
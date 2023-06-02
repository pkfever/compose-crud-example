package com.test.myapplication.di

import com.squareup.moshi.Moshi
import com.test.myapplication.data.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://mentalminutes.cubestagearea.xyz"

    @Provides
    @Singleton
    fun provideMoshiConverter(): Moshi {
        return Moshi.Builder()
//            .add(M)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .writeTimeout(60000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS)
            .callTimeout(100000, TimeUnit.MILLISECONDS)
            .connectTimeout(60000, TimeUnit.MILLISECONDS)
            .addInterceptor(HeaderInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideRetrofitService(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        moshiConverter: Moshi,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
            .client(okHttpClient)
            .build()
    }
}
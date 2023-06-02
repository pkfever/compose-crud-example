package com.test.myapplication.data.interceptor

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Interceptor
import okhttp3.Response

@Module
@InstallIn(SingletonComponent::class)
class HeaderInterceptor : Interceptor {

    private var accessToken = ""
    private val uiScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", accessToken)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
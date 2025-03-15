package com.example.myapplication.di

import android.R.attr.level
import com.example.myapplication.Util
import com.example.myapplication.remote.ApiService
import com.example.myapplication.remote.ApiServiceImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient{
        return HttpClient(Android){
            install(Logging){
                level=LogLevel.ALL
            }
            install(DefaultRequest){
                url(Util.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation) {
              //  json()
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient):ApiService=ApiServiceImpl(httpClient)

    @Provides
    fun provideDispatcher():CoroutineDispatcher=Dispatchers.Default
}
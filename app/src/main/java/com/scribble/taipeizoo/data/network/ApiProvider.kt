package com.scribble.taipeizoo.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://data.taipei/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getHttpClient())
        .build()

    private fun getHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        return httpClient.build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}
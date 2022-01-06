package com.example.hoangcv2_task

import com.example.hoangcv2_task.api.AccountActivityService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConfig {

    private var retrofitService: AccountActivityService? = null

    fun getInstance(): AccountActivityService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.10")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
            retrofitService = retrofit.create(AccountActivityService::class.java)
        }
        return retrofitService!!
    }
}
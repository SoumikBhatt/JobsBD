package com.soumik.bdjobs.networkflow

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.soumik.bdjobs.BuildConfig
import com.soumik.bdjobs.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


//
// Created by Soumik on 12/24/2020.
// Copyright (c) 2020 Soumik.  All rights reserved.
//
object RetrofitClient {

    private val client = OkHttpClient.Builder()
            .addInterceptor(
                    LoggingInterceptor.Builder()
                            .loggable(BuildConfig.DEBUG)
                            .setLevel(Level.BASIC)
                            .log(Platform.INFO)
                            .request("LOG")
                            .response("LOG")
                            .executor(Executors.newSingleThreadExecutor())
                            .build()
            )
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    var webService:WebService = retrofit.create(WebService::class.java)

}
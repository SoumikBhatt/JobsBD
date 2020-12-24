package com.soumik.bdjobs.networkflow

import com.soumik.bdjobs.data.JobInfoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


//
// Created by Soumik on 12/24/2020.
// Copyright (c) 2020 Soumik.  All rights reserved.
//
interface WebService {

    @GET("InterviewJson.json")
    suspend fun jobList():Response<JobInfoModel>
}
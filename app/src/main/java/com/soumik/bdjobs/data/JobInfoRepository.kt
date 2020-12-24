package com.soumik.bdjobs.data

import android.util.Log
import com.soumik.bdjobs.networkflow.RetrofitClient
import com.soumik.bdjobs.utils.BASE_URL
import com.soumik.bdjobs.utils.FAILURE_MESSAGE
import com.soumik.bdjobs.utils.NETWORK_FAILURE
import com.soumik.bdjobs.utils.Resource
import java.io.IOException


//
// Created by Soumik on 12/25/2020.
// Copyright (c) 2020 Soumik.  All rights reserved.
//



class JobInfoRepository {

    companion object {
        private const val TAG = "JOB_INFO_REPO"
    }

    suspend fun getJobList():Resource<JobInfoModel> {

        return  try {
            val response = RetrofitClient.webService.jobList()

            when(response.code()) {
                200-> {
                    if (response.body()!=null) {
                        if (response.body()!!.message=="Success") {
                            Resource.success(response.body()!!)
                        } else Resource.error(response.body()!!.message)
                    } else {
                        Log.e(TAG, "getJobList: Response body is null")
                        Resource.error(FAILURE_MESSAGE)
                    }
                }
                else -> {
                    Resource.error("$FAILURE_MESSAGE: ${response.code()}")
                }

            }

        } catch (t: Throwable) {
            t.printStackTrace()
            Log.e(TAG, "getJobList: ${t.localizedMessage}")
            when (t) {
                is IOException -> Resource.error(NETWORK_FAILURE)
                else -> Resource.error(FAILURE_MESSAGE)
            }
        }
    }
}
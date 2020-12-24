package com.soumik.bdjobs.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soumik.bdjobs.data.JobInfoModel
import com.soumik.bdjobs.data.JobInfoRepository
import com.soumik.bdjobs.utils.*


//
// Created by Soumik on 12/25/2020.
// Copyright (c) 2020 Soumik.  All rights reserved.
//
class JobInfoViewModel:ViewModel() {

    companion object {
        private const val TAG = "JOB_INFO_VIEW_MODEL"
    }

    private val mJobInfoRepository = JobInfoRepository()



    suspend fun getJobList():LiveData<Resource<JobInfoModel>> {

        val infoLiveData = MutableLiveData<Resource<JobInfoModel>>()
        infoLiveData.postValue(Resource.loading())

        if (hasInternetConnection()) {
            val resource = mJobInfoRepository.getJobList()

            when(resource.status) {
                Status.SUCCESS -> {
                    infoLiveData.postValue(Resource.success(resource.data!!))
                }
                Status.ERROR -> {
                    infoLiveData.postValue(Resource.error(resource.error))
                }
                Status.LOADING-> {
                    infoLiveData.postValue(Resource.loading())
                }
            }

        } else infoLiveData.postValue(Resource.error(NO_CONNECTION))

        return  infoLiveData;
    }
}
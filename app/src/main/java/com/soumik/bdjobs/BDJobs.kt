package com.soumik.bdjobs

import android.app.Application
import android.content.Context

//
// Created by Soumik on 12/24/2020.
//
class BDJobs:Application() {

    companion object {
        lateinit var mContext:Context
    }

    override fun onCreate() {
        super.onCreate()

        mContext = applicationContext
    }
}
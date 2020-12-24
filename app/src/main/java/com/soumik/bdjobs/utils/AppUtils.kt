package com.soumik.bdjobs.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.soumik.bdjobs.BDJobs

//
// Created by Soumik on 12/24/2020.
//

fun lightStatusBar(activity: Activity, value:Boolean){
    if (value){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}

fun toolbarStyle(context: AppCompatActivity, toolbar: Toolbar, title: String) {

    (context).setSupportActionBar(toolbar)
    context.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    context.supportActionBar!!.setDisplayShowHomeEnabled(true)
    context.supportActionBar!!.setDisplayShowTitleEnabled(true)

    context.supportActionBar!!.title = title
}

fun showToast(context: Context, message:String, length:Int) {
    Toast.makeText(context,message,length).show()
}

fun hasInternetConnection(): Boolean {
    val connectivityManager = BDJobs.mContext.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo?.run {
            return when (type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
    }
    return false
}

fun String.addOrdinals(): String = when (this) {
    1.toString() -> "" + this + "st"
    "01" -> "" + this + "st"
    21.toString() -> "" + this + "st"
    31.toString() -> "" + this + "st"

    2.toString() -> "" + this + "nd"
    "02" -> "" + this + "nd"
    22.toString() -> "" + this + "nd"

    3.toString() -> "" + this + "rd"
    "03" -> "" + this + "rd"
    23.toString() -> "" + this + "rd"

    else -> "" + this + "th"
}
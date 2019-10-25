package com.example.luckyface.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.luckyface.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    private val context : Context

) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isNetworkAvailable())
            throw NoInternetException("Internet is Not available")

        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable() : Boolean{

        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}
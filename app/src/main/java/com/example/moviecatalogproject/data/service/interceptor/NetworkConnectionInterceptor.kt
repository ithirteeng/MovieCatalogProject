package com.example.moviecatalogproject.data.service.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.UnknownHostException


class NetworkConnectionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("CONNECTION", "test")

        return try {
            chain.proceed(request)
        } catch (e: IOException) {
            handleError(e)
        }
    }

    private fun handleError(error: IOException): Nothing {
        when (error) {
            is UnknownHostException ->
                Log.d("CONNECTION", "trouble")
        }
        throw NoConnectivityException()
    }


}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}
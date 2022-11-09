package com.example.moviecatalogproject.data.service.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.UnknownHostException


class NetworkConnectionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null

        return try {
            response = chain.proceed(request)
            response
        } catch (e: IOException) {
            response?.close()
            handleError(e)
            chain.proceed(request)
        }
    }

    private fun handleError(error: IOException) {
        when (error) {
            is UnknownHostException ->
                Log.d("CONNECTION", "trouble")
        }
        //throw NoConnectivityException()
    }


}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}
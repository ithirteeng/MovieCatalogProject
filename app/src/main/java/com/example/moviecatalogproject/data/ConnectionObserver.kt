package com.example.moviecatalogproject.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

object ConnectionObserver {
    fun observeConnection(
        context: Context,
        onConnectionLost: () -> Unit,
        onConnectionAvailable: () -> Unit
    ) {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d("CONNECTION", "available")
                onConnectionAvailable()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d("CONNECTION", "lost")
                onConnectionLost()
            }
        }

        val connectivityManager =
            getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)


    }
}
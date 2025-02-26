package com.jobassignmentproject.PresentationLayer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NetworkObserver {

    private val _networkStatus = MutableLiveData<String>()
    val networkStatus: LiveData<String> get() = _networkStatus

    private lateinit var connectivityManager: ConnectivityManager

    private val networkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            checkNetworkSpeed(network)
        }

        override fun onLost(network: Network) {
            _networkStatus.postValue("No Internet Connection")
        }
    }

    fun init(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    private fun checkNetworkSpeed(network: Network) {
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            val speedKbps = capabilities.linkDownstreamBandwidthKbps
            if (speedKbps in 1..150) { // Slow network threshold
                _networkStatus.postValue("Slow Internet Connection")
            }
        }
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}


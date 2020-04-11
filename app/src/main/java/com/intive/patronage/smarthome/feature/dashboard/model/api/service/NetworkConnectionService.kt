package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import android.content.Context
import android.net.*
import io.reactivex.subjects.BehaviorSubject


class NetworkConnectionService(context: Context) {

    val connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

    val subject: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()
    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            subject.onNext(false)
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            subject.onNext(true)
        }
    }

    init {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun unregisterCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

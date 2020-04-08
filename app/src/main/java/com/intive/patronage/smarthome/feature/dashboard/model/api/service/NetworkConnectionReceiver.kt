package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.*
import android.util.Log
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableError
import io.reactivex.subjects.BehaviorSubject


class NetworkConnectionReceiver(context: Context): ConnectivityManager.NetworkCallback() {

    val subject: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()
//    val observer: Observable<Boolean> = Observable.just(true)
    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            subject.onNext(false)
            Log.d("XD", subject.value.toString())
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            subject.onNext(true)
            Log.d("XD", subject.value.toString())
        }
    }

    init {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

//            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


//        connectivityManager.activeNetworkInfo
//        val mobile: NetworkInfo.State = connectivityManager.getNetworkInfo(0).getState()
//
//        val wifi: NetworkInfo.State = connectivityManager.getNetworkInfo(1).getState()
//
//
//        if (mobile === NetworkInfo.State.CONNECTED || mobile === NetworkInfo.State.CONNECTING) {
//            Log.d("XD", "yes cel")
//            //behavaiour
//        }
//        else Log.d("XD", "no cel")
//
//        if (wifi === NetworkInfo.State.CONNECTED || wifi === NetworkInfo.State.CONNECTING) {
//            Log.d("XD", "yes wifi")
//        }
//        else Log.d("XD", "no wifi")

}

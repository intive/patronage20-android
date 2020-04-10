package com.intive.patronage.smarthome.feature.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.NetworkConnectionService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SmartHomeActivityViewModel(private val networkConnectionService: NetworkConnectionService) : ViewModel() {

    val networkConnection = MutableLiveData<Boolean>()
    private var networkState: Disposable? = null

    init {
        fetchConnectionStatus()
    }

    fun fetchConnectionStatus() {
        networkState = networkConnectionService.subject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnDispose { networkConnectionService.unregisterCallback() }
            .subscribe {
                networkConnection.value = it
            }
    }

    override fun onCleared() {
        super.onCleared()
        networkState?.dispose()
    }
}
package com.intive.patronage.smarthome.feature.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.NetworkConnectionService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SmartHomeActivityViewModel(
    private val dashboardService: DashboardService,
    private val networkConnectionService: NetworkConnectionService
): ViewModel() {

    val networkConnection = MutableLiveData<Boolean>().apply { value = true }
    val apiConnection = MutableLiveData<Boolean>().apply { value = true }
    private var networkState: Disposable? = null
    private var apiState: Disposable? = null

    init {
        fetchConnectionStatus()
        fetchAPIStatus()
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

    fun fetchAPIStatus() {
        apiState = dashboardService.dashboardBehaviorSubjectError
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                apiConnection.value = it
            }
    }

    override fun onCleared() {
        super.onCleared()
        networkState?.dispose()
        apiState?.dispose()

    }
}
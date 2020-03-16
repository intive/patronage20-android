package com.intive.patronage.smarthome.splashscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.dashboard.model.Dashboard
import com.intive.patronage.smarthome.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SplashScreenViewModel(private val dashboardService: DashboardService) : ViewModel() {

    private val minWaitTime = 5L
    private val maxWaitTime = 30L

    private var dashboard = MutableLiveData<Dashboard>()
    var error = MutableLiveData<Boolean>().apply { value = false }
    var complete = MutableLiveData<Boolean>().apply { value = false }
    private var dashboardCall: Disposable? = null

    fun getSensors() {

        dashboardCall = dashboardService.getDashboard().toObservable()
            .delay(minWaitTime, TimeUnit.SECONDS, true)
            .timeout(maxWaitTime, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dashboard.value = it
            }, { error.value = true }, { complete.value = true })
    }


    override fun onCleared() {
        super.onCleared()
        dashboardCall?.dispose()
    }


}
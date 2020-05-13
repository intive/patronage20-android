package com.intive.patronage.smarthome.feature.splashcreen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val MIN_WAIT_TIME = 5L
const val MAX_WAIT_TIME = 30L

class SplashScreenViewModel(dashboardService: DashboardService) : ViewModel() {


    private var dashboard = MutableLiveData<Dashboard>()
    var error = MutableLiveData<Boolean>().apply { value = false }
    var complete = MutableLiveData<Boolean>().apply { value = false }
    private var dashboardCall: Disposable? = null

    init {
        dashboardCall = dashboardService.fetchDashboardWithDelay(MIN_WAIT_TIME, MAX_WAIT_TIME)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dashboard.value = it.first },
                { error.value = true },
                { complete.value = true }
            )
    }

    override fun onCleared() {
        super.onCleared()
        dashboardCall?.dispose()
    }
}
package com.intive.patronage.smarthome.feature.blind.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.blind.view.BlindView
import com.intive.patronage.smarthome.feature.blind.view.setOnLayoutChangeListener
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BlindDetailsViewModel(
    private var dashboardService: DashboardService,
    var canvas: BlindView,
    private val id: Int
) : ObservableViewModel() {

    var position: Int = 0
    private var percent: String = "$position %"
    private var disposable: Disposable? = null

    init {
        loadBlind()
    }

    private fun loadBlind() {
        disposable = dashboardService.getBlindById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    position = it.position
                    setPercent("$position %")
                }
                else Log.d("Exception", "NULL")
            },{
                Log.d("Exception", "ERROR")
            })
    }

    fun blindUp() {
        canvas.blindUp()
        if (position > 0) position--
        setPercent("$position %")
        canvas.invalidate()
    }

    fun blindDown() {
        canvas.blindDown()
        if (position < 100) position++
        setPercent("$position %")
        canvas.invalidate()
    }

    @Bindable
    fun getPercent() = this.percent

    fun setPercent(value: String) {
        if (this.percent != value) this.percent = value
        notifyPropertyChanged(0)
    }
}
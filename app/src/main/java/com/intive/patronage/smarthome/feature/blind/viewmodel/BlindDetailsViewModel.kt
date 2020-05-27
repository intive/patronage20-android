package com.intive.patronage.smarthome.feature.blind.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.blind.model.BlindSensor
import com.intive.patronage.smarthome.feature.blind.model.api.BlindDetailsService
import com.intive.patronage.smarthome.feature.blind.view.BlindViewEventListener
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val BLIND_SENSOR_TYPE = "windowBlind"

class BlindDetailsViewModel(
    private var dashboardService: DashboardService,
    var blindViewEventListener: BlindViewEventListener,
    private val id: Int,
    private val blindDetailsService: BlindDetailsService
) : ObservableViewModel() {

    var position: Int = 0
    private var percent: String = "$position %"
    private var disposable: Disposable? = null
    private var updatePositionCall: Disposable? = null

    init {
        loadBlind()
    }

    fun loadBlind() {
        disposable = dashboardService.getBlindById(71) //dashboardService.getBlindById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    position = 100-it.position
                    blindViewEventListener.setStartingPosition(position)
                    setPercent("${100-position} %")
                }
                else Log.d("Exception", "NULL")
            },{
                Log.d("Exception", it.toString())
            })
    }

    private fun updateBlindPosition() {
        val blind = BlindSensor(71, BLIND_SENSOR_TYPE, 100 - position) //BlindSensor(id, BLIND_SENSOR_TYPE, 100 - position)
        updatePositionCall = blindDetailsService.updateBlindPosition(blind)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                blindViewEventListener.showToast(R.string.apply_toast)
            }, {
                blindViewEventListener.showToast(R.string.update_value_toast_error)
            })
    }

    @Bindable
    fun getPercent() = this.percent

    fun setPercent(value: String) {
        if (this.percent != value) this.percent = value
        notifyPropertyChanged(0)
    }

    fun onApplyClicked() {
        updateBlindPosition()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        updatePositionCall?.dispose()
    }
}
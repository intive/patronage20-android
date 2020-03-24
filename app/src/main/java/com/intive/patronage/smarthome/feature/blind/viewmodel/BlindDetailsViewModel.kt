package com.intive.patronage.smarthome.feature.blind.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.blind.view.BlindViewEventListener
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BlindDetailsViewModel(
    private var dashboardService: DashboardService,
    var blindViewEventListener: BlindViewEventListener,
    private val id: Int
) : ObservableViewModel() {

    var position: Int = 0
    private var percent: String = "$position %"
    private var disposable: Disposable? = null

    init {
        loadBlind()
    }

    fun loadBlind() {
        disposable = dashboardService.getBlindById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    position = it.position
                    blindViewEventListener.setStartingPosition(position)
                    setPercent("$position %")
                }
                else Log.d("Exception", "NULL")
            },{
                Log.d("Exception", "ERROR")
            })
    }

    @Bindable
    fun getPercent() = this.percent

    fun setPercent(value: String) {
        if (this.percent != value) this.percent = value
        notifyPropertyChanged(0)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
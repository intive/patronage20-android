package com.intive.patronage.smarthome.feature.home.viewmodel

import android.app.Dialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.intive.patronage.smarthome.feature.home.view.DialogSensorMock
import io.reactivex.disposables.Disposable

class SensorDialogViewModel() : ViewModel() {
    val items  = mutableListOf(DialogSensorMock(123f, 12f)
        , DialogSensorMock(13f, 153f)
        , DialogSensorMock(13f, 153f)
        , DialogSensorMock(13f, 153f)
        , DialogSensorMock(13f, 153f)
        , DialogSensorMock(13f, 153f)
        , DialogSensorMock(13f, 153f)
        , DialogSensorMock(13f, 153f)
        , DialogSensorMock(13f, 153f)
        , DialogSensorMock(13f, 153f))//= MutableLiveData<List<DialogSensorMock>>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null

    init {

    }
}
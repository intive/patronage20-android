package com.intive.patronage.smarthome.feature.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.home.model.HomeRepository
import com.intive.patronage.smarthome.feature.home.view.HomeFragment
import io.reactivex.disposables.Disposable
import org.koin.core.context.KoinContextHandler.get
import org.koin.java.KoinJavaComponent.inject

class SensorDialogViewModel(repo: HomeRepository) : ViewModel() {

    val items = repo.sensorList
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null

    init {

    }
}
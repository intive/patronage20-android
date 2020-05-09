package com.intive.patronage.smarthome.feature.developer_settings.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.developer_settings.DeveloperSettings
import com.intive.patronage.smarthome.feature.developer_settings.model.DeveloperSettingsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DeveloperSettingsViewModel(
    private val developerSettings: DeveloperSettings,
    private val developerSettingsService: DeveloperSettingsService) : ViewModel() {

    val version : ObservableField<String> = ObservableField()
    private var deleteSensorsCall: Disposable? = null

    init {
        version.apply {
            this.set(developerSettings.getVersion())
        }
    }

    fun deleteAllSensors() {
        deleteSensorsCall = developerSettingsService.deleteAllSensors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
    }

    override fun onCleared() {
        super.onCleared()
        deleteSensorsCall?.dispose()
    }

    fun isDebugMode() = developerSettings.isDebugMode()

}
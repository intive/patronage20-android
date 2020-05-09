package com.intive.patronage.smarthome.feature.developer_settings.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.ActivityDeveloperSettingsBinding
import com.intive.patronage.smarthome.feature.developer_settings.viewmodel.DeveloperSettingsViewModel
import com.intive.patronage.smarthome.navigator.DeveloperSettingsCoordinator
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeveloperSettingsActivity : AppCompatActivity() {
    private val developerSettingsCoordinator: DeveloperSettingsCoordinator by inject { parametersOf(this) }
    private val model: DeveloperSettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding =  DataBindingUtil.setContentView<ActivityDeveloperSettingsBinding>(this, R.layout.activity_developer_settings)
        activityMainBinding.developerSettingsViewModel = model
    }

    override fun onBackPressed() {
        developerSettingsCoordinator.goBack()
    }
}

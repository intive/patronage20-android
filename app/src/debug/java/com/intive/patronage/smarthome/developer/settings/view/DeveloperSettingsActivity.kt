package com.intive.patronage.smarthome.developer.settings.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.intive.patronage.smarthome.BuildConfig
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.ActivityDeveloperSettingsBinding
import com.intive.patronage.smarthome.developer.settings.DeveloperSettings
import com.intive.patronage.smarthome.developer.settings.viewmodel.DeveloperSettingsViewModel
import com.intive.patronage.smarthome.navigator.DeveloperSettingsCoordinator
import com.intive.patronage.smarthome.navigator.SplashScreenCoordinator
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeveloperSettingsActivity : AppCompatActivity() {

    private val developerSettingsCoordinator: DeveloperSettingsCoordinator by inject { parametersOf(this) }
    private val model: DeveloperSettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_settings)
        val binding: ActivityDeveloperSettingsBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_developer_settings)

        binding.developer = DeveloperSettings()
    }

    override fun onBackPressed() {
        developerSettingsCoordinator.goBack()
    }
}

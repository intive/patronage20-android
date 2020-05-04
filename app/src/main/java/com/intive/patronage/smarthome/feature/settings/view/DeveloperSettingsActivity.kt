package com.intive.patronage.smarthome.feature.settings.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.intive.patronage.smarthome.common.PreferencesWrapper
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.ActivityDeveloperSettingsBinding
import com.intive.patronage.smarthome.feature.settings.viewmodel.DeveloperSettingsViewModel
import com.intive.patronage.smarthome.navigator.DeveloperSettingsCoordinator
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeveloperSettingsActivity : AppCompatActivity() {

    private val preferences: PreferencesWrapper by inject { parametersOf(applicationContext) }
    private val developerSettingsCoordinator: DeveloperSettingsCoordinator by inject { parametersOf(this) }
    private val model: DeveloperSettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding =  DataBindingUtil.setContentView<ActivityDeveloperSettingsBinding>(this, R.layout.activity_developer_settings)
        activityMainBinding.developerSettingsViewModel = model
        setupDarkModeSwitch()
    }

    override fun onBackPressed() {
        developerSettingsCoordinator.goBack()
    }

    private fun setupDarkModeSwitch(){
        val switch = findViewById<Switch>(R.id.darkModeSwitch)
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            switch.isChecked = true
        }
        switch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                setDarkMode(true)
            else
                setDarkMode(false)
        }
    }

    private fun setDarkMode(darkMode: Boolean){
        if(darkMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        preferences.setDarkModePreference(darkMode)
    }
}

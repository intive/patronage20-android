package com.intive.patronage.smarthome.feature.dashboard.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.SmartHomeAlertDialog
import com.intive.patronage.smarthome.feature.developer.viewmodel.DeveloperSettingsViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.DashboardViewModel
import com.intive.patronage.smarthome.feature.splashcreen.viewmodel.SplashScreenViewModel
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SmartHomeActivity : AppCompatActivity() {

    private val dashboardCoordinator: DashboardCoordinator by inject { parametersOf(this) }
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private val alertDialog: SmartHomeAlertDialog by inject()
    private val developerSettingsViewModel : DeveloperSettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_home_activity)

        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            dashboardCoordinator.goToSmartHome()
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        dashboardViewModel.error.observe(this, Observer { error ->
            if (error) alertDialog.showSmartHomeDialog(
                this, R.string.error_title,
                R.string.connection_error_message
            ) { finish() }
        })
    }

    override fun onBackPressed() {
        dashboardCoordinator.goBack()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (developerSettingsViewModel.isDebugMode()) {
            menuInflater.inflate(R.menu.menu_developer_settings, menu)
            true
        } else {
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.developer_settings -> {
                dashboardCoordinator.goToDeveloperSettings()
            }
        }
        return true
    }
}
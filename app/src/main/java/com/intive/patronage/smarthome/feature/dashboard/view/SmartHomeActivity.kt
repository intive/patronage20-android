package com.intive.patronage.smarthome.feature.dashboard.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.SmartHomeErrorSnackbar
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.NetworkConnectionService
import com.intive.patronage.smarthome.feature.developer.viewmodel.DeveloperSettingsViewModel
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.DashboardViewModel
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.SmartHomeActivityViewModel
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SmartHomeActivity : AppCompatActivity() {

    private val dashboardCoordinator: DashboardCoordinator by inject { parametersOf(this) }
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private val alertSnackbar: SmartHomeErrorSnackbar by inject { parametersOf(this)}
    private val developerSettingsViewModel: DeveloperSettingsViewModel by viewModel()
    private val networkConnectionService: NetworkConnectionService by inject { parametersOf(this) }
    private val smartHomeActivityViewModel: SmartHomeActivityViewModel by viewModel { parametersOf(networkConnectionService)}

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
        var networkError = false
        smartHomeActivityViewModel.networkConnection.observe(
            this,
            Observer { networkConnection ->
                if (!networkConnection) {
                    alertSnackbar.showSnackbar(getString(R.string.network_connection_error))
                    networkError = true
                } else
                    alertSnackbar.hideSnackbar()
            })
        dashboardViewModel.error.observe(this, Observer { error ->
            if (error && !networkError)
                alertSnackbar.showSnackbar(getString(R.string.api_connection_error))
             else
                alertSnackbar.hideSnackbar()
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

    fun hideLogo(){
        toolbarLogo.visibility = View.GONE
    }

    fun showLogo(){
        toolbarLogo.visibility = View.VISIBLE
    }
}
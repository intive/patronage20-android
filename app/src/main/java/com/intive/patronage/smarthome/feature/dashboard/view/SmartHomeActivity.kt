package com.intive.patronage.smarthome.feature.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.DeeplinkService
import com.intive.patronage.smarthome.common.SmartHomeErrorSnackbar
import com.intive.patronage.smarthome.feature.blind.view.BlindDetailsFragment
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.NetworkConnectionService
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.SmartHomeActivityViewModel
import com.intive.patronage.smarthome.feature.hvac.view.HvacDetailsFragment
import com.intive.patronage.smarthome.feature.light.view.LightsDetailsFragment
import com.intive.patronage.smarthome.feature.login.authentication.AuthenticationService
import com.intive.patronage.smarthome.feature.settings.view.SettingsFragment
import com.intive.patronage.smarthome.feature.temperature.view.TemperatureDetailsFragment
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SmartHomeActivity : AppCompatActivity() {

    private val dashboardCoordinator: DashboardCoordinator by inject {
        parametersOf(this)
    }
    private val authenticationService: AuthenticationService by inject {
        parametersOf(this)
    }
    private val alertSnackbar: SmartHomeErrorSnackbar by inject {
        parametersOf(this)
    }
    private val networkConnectionService: NetworkConnectionService by inject {
        parametersOf(this)
    }
    private val smartHomeActivityViewModel: SmartHomeActivityViewModel by viewModel {
        parametersOf(networkConnectionService)
    }
    private val deeplinkService: DeeplinkService by inject {
        parametersOf(dashboardCoordinator)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        deeplinkService.handleDeeplinkRedirectionInDashboard(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_home_activity)
        setSupportActionBar(toolbar)
        deeplinkService.handleDeeplinkRedirectionInDashboard(intent, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        observeViewModel()
        authenticationService.initAuthFirebase()
        authenticationService.initGoogleSignIn()
        if (!authenticationService.isUserLogged()) {
            dashboardCoordinator.goToLoginScreen()
        }
    }

    private fun observeViewModel() {
        smartHomeActivityViewModel.networkConnection.observe(
            this, Observer { networkConnection ->
                if (!networkConnection)
                    alertSnackbar.showSnackbar(getString(R.string.network_connection_error))
                else if (networkConnection && alertSnackbar.snackbar.isShown)
                    alertSnackbar.hideSnackbar()
            })
    }

    override fun onBackPressed() {
        dashboardCoordinator.goBack()
        setToolbarOnBackPressed(findPenultimateFragmentOnBackStack())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_developer_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.developer_settings) {
            dashboardCoordinator.goToSettingsScreen()
        }
        return true
    }

    fun hideLogo() {
        toolbarLogo.visibility = View.GONE
    }

    fun showLogo() {
        toolbarLogo.visibility = View.VISIBLE
    }

    fun hideTitle() {
        toolbar.title = ""
    }

    fun hideArrowBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun setToolbarAfterReturnFromDetailScreen() {
        hideArrowBack()
        hideTitle()
        showLogo()
    }

    fun setToolbarForDetailsScreen(title: Int, displayHomeAsUpEnabled: Boolean) {
        toolbar.title = resources.getString(title)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
        hideLogo()
    }

    fun setToolbarForTemperatureScreen() {
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun findPenultimateFragmentOnBackStack() : Fragment? {
        val valueToSubtractToGetPenultimateIndex = 2
        val penultimateFragmentIndex = supportFragmentManager.backStackEntryCount - valueToSubtractToGetPenultimateIndex
        return if (penultimateFragmentIndex >= 0) supportFragmentManager.fragments[penultimateFragmentIndex] else null
    }

    fun setToolbarOnBackPressed(penultimateFragment: Fragment?) {

        when (penultimateFragment?.tag) {
            "${LightsDetailsFragment::class.java}" -> {
                setToolbarForDetailsScreen(R.string.lights_details_appbar, true)
            }
            "${HvacDetailsFragment::class.java}" -> {
                setToolbarForDetailsScreen(R.string.hvac_details_appbar, true)
            }
            "${TemperatureDetailsFragment::class.java}" -> {
                setToolbarForTemperatureScreen()
            }
            "${BlindDetailsFragment::class.java}" -> {
                setToolbarForDetailsScreen(R.string.blind_details_appbar, true)
            }
            "${SmartHomeFragment::class.java}" -> {
                setToolbarAfterReturnFromDetailScreen()
            }
            "${SettingsFragment::class.java}" -> {
                setToolbarForDetailsScreen(R.string.settings_toolbar, true)
            }
        }
    }
}

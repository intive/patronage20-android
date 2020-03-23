package com.intive.patronage.smarthome.feature.dashboard.view

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SmartHomeActivity : AppCompatActivity() {

    private val dashboardCoordinator: DashboardCoordinator by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_home_activity)

        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            dashboardCoordinator.goToDashboard()
        }

        val dashboardService = get<DashboardService>()
        dashboardService.getDashboard()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("DASHBOARD_SERVICE", it.toString())
            }, {
                Log.d("DASHBOARD_SERVICE", it.toString())
            })

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        dashboardCoordinator.goBack()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val isDebuggable =
            0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        if (isDebuggable) {
            menuInflater.inflate(R.menu.menu_developer_settings, menu)
            return true
        }
        return false
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
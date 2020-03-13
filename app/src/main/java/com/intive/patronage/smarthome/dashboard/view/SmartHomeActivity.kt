package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.model.api.service.DashboardService
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.ext.android.get


class SmartHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_home_activity)

        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, LightsDetailsFragment()).addToBackStack(null)
            .commit()

        val dashboardService = get<DashboardService>()
        Log.d("DASHBOARD_SERVICE", dashboardService.getDashboard().toString())

        toolbar.setNavigationOnClickListener {
            //TODO: implement navigator
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        //navigator.quit()
    }

}
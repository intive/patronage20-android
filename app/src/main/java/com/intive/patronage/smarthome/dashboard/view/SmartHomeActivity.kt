package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class SmartHomeActivity : AppCompatActivity() {


    private val navigator = Navigator(this)
    private val dashboardCoordinator = DashboardCoordinator(navigator)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_home_activity)

        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            dashboardCoordinator.goToDashboard()
        }
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment, DashboardFragment()).addToBackStack(null)
//            .commit()

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
            //TODO: implement navigator
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        dashboardCoordinator.goBack()
    }

}
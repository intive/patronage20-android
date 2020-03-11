package com.intive.patronage.smarthome

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import com.intive.patronage.smarthome.spashscreen.SmartHomeAlertDialog
import com.intive.patronage.smarthome.spashscreen.SplashScreenViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val alertDialog: SmartHomeAlertDialog by inject()
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()
   // private val dashboardCoordinator: DashboardCoordinator by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        splashScreenViewModel.getSensors()
        observeViewModel()


    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) fullScreen()
    }

    private fun observeViewModel() {
        splashScreenViewModel.error.observe(this, Observer { error ->
            if (error) alertDialog.connectionError(this)

        })
        splashScreenViewModel.complete.observe(this, Observer { complete ->
            if (complete) Toast.makeText(this, "Next Activity", Toast.LENGTH_LONG).show()
           // dashboardCoordinator.goToDashboard()

        })
    }

    // Setting fullscreen
    private fun fullScreen() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


}

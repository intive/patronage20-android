package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.dashboard_appbar)
        setContentView(R.layout.dashboard_activity)
    }

}
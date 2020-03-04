package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R

class SmartHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //navigator.quit()
    }

}
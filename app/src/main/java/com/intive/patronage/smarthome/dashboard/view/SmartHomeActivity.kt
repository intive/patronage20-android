package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import org.koin.core.context.stopKoin

class SmartHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_home_activity)
    }

    override fun onBackPressed() {
        stopKoin()
        super.onBackPressed()
        //navigator.quit()
    }

}
package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.smart_home_activity.*


class SmartHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_home_activity)

        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.lights_details_fragment, LightsDetailsFragment()).addToBackStack(null)
            .commit()

        toolbar.setNavigationOnClickListener {
            //TODO: implement navigator
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        //navigator.quit()
    }

}
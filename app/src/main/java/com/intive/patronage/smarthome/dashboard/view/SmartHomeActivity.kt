package com.intive.patronage.smarthome.dashboard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.activity_smart_home.*

class SmartHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_home)

        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, LightsDetailsFragment()).addToBackStack(null)
            .commit()

        toolbar.setNavigationOnClickListener {
            //TODO: implement navigator
        }
    }
}

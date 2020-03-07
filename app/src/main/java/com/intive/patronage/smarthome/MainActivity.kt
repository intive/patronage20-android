package com.intive.patronage.smarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.intive.patronage.smarthome.navigator.CoordinatorDashBoard
import com.intive.patronage.smarthome.navigator.Navigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

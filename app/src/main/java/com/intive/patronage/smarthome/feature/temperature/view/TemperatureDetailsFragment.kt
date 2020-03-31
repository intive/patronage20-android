package com.intive.patronage.smarthome.feature.temperature.view

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.temperature.viewmodel.TemperatureDetailsViewModel
import kotlinx.android.synthetic.main.fragment_temperature_details.*
import kotlinx.android.synthetic.main.fragment_temperature_details.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class TemperatureDetailsFragment : Fragment(){

    private val temperatureDetailsViewModel by viewModel<TemperatureDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.hide()
        (activity as AppCompatActivity).window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val view = inflater.inflate(R.layout.fragment_temperature_details, container, false)

        temperatureDetailsViewModel.data.observe(this, Observer {
            if (it != null) {
                view.graphView.setData(it, 1,1, 8f)
            }
        })

        for (button in view.buttonsLayout) button.isEnabled = true

        val selectedButton = if (savedInstanceState != null) {
            view.buttonsLayout.getChildAt(savedInstanceState.getInt("selectedButton") - 1)
        } else {
            view.buttonsLayout.getChildAt(0)
        }
        selectedButton.isEnabled = false

        for (button in view.buttonsLayout) {
            button.setOnClickListener {
                setIsEnabled(it)
            }
        }

        return view
    }

    private fun setIsEnabled(it: View) {
        for (button in buttonsLayout) {
            button.isEnabled = button != it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var selectedButton = 0
        for (button in buttonsLayout) {
            selectedButton++
            if (!button.isEnabled) outState.putInt("selectedButton", selectedButton)
        }
    }
}

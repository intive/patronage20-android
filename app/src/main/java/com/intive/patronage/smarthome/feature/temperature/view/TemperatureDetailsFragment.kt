package com.intive.patronage.smarthome.feature.temperature.view

import android.os.Bundle
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
import org.koin.core.parameter.parametersOf

class TemperatureDetailsFragment : Fragment(){

    private val temperatureDetailsViewModel by viewModel<TemperatureDetailsViewModel> {
        parametersOf(this.arguments?.getInt("ID"))
    }

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
                view.graphView.setData(it)
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

        when(it) {
            button1h -> temperatureDetailsViewModel.subscribe(60, 3)
            button3h -> temperatureDetailsViewModel.subscribe(180, 6)
            button6h -> temperatureDetailsViewModel.subscribe(360, 12)
            button12h -> temperatureDetailsViewModel.subscribe(720, 24)
            button24h -> temperatureDetailsViewModel.subscribe(1440, 48)
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

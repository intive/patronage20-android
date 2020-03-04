package com.intive.patronage.smarthome.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.viewmodel.LightsDetailsViewModel
import kotlinx.android.synthetic.main.fragment_lights_details.*
import kotlinx.android.synthetic.main.fragment_lights_details.view.*

class LightsDetailsFragment : Fragment() {

    var redValue = 98
    var greenValue = 0
    var blueValue = 238

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lights_details, container, false)

        val lightsDetailsViewModel = ViewModelProviders.of(this).get(LightsDetailsViewModel::class.java)

        lightsDetailsViewModel.getLights().observe(this, Observer {
            //TODO: update UI
        })

        view.okButton.setOnClickListener {
            //TODO: send data to API
        }

        view.redSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                redValue = progress
                val redText = "R: $redValue"
                view.redTextView.text = redText
                setCurrentColor()
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        view.greenSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                greenValue = progress
                val greenText = "R: $greenValue"
                view.greenTextView.text = greenText
                setCurrentColor()
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        view.blueSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                blueValue = progress
                val blueText = "R: $blueValue"
                view.blueTextView.text = blueText
                setCurrentColor()
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        return view
    }

    fun setCurrentColor() {
        val color = Color.rgb(redValue, greenValue, blueValue)
        colorView.setBackgroundColor(color)
    }
}

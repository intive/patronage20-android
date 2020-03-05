package com.intive.patronage.smarthome.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
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

        lightsDetailsViewModel.getLights().observe(viewLifecycleOwner, Observer {
            convertHSVtoRGB(it.hue, it.saturation, it.value)
            //TODO: update UI
        })

        view.okButton.setOnClickListener {
            convertRGBtoHSV()
            //TODO: send data to API
        }

        view.cancelButton.setOnClickListener {
            //TODO: show previous colors
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
                val greenText = "G: $greenValue"
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
                val blueText = "B: $blueValue"
                view.blueTextView.text = blueText
                setCurrentColor()
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        return view
    }

    fun convertRGBtoHSV() {
        val hsv = FloatArray(3)
        Color.RGBToHSV(redValue, greenValue, blueValue, hsv)
        Log.d("HUE", hsv[0].toString())
        Log.d("SATURATION", hsv[1].toString())
        Log.d("VALUE", hsv[2].toString())
    }

    fun convertHSVtoRGB(hue: Int, saturation: Int, value: Int) {
        val hsv = floatArrayOf(hue.toFloat(), saturation.toFloat(), value.toFloat())
        val rgb = Color.HSVToColor(hsv)
        /*redValue = rgb.red
        greenValue = rgb.green
        blueValue = rgb.blue*/
    }

    fun setCurrentColor() {
        val color = Color.rgb(redValue, greenValue, blueValue)
        colorView.setBackgroundColor(color)
    }
}

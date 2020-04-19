package com.intive.patronage.smarthome.feature.light.view

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.FragmentLightsDetailsBinding
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LightsDetailsFragment : Fragment(), ColorPickerEventListener {

    private val lightsDetailsViewModel by viewModel<LightsDetailsViewModel> {
        parametersOf(this ,this.arguments?.getInt("ID"))
    }

    private lateinit var binding: FragmentLightsDetailsBinding

    private lateinit var brightnessView: BrightnessSeekBar
    private lateinit var brightnessBitmap: Bitmap
    private lateinit var brightnessCanvas: Canvas

    private val imageQuality = 720
    val radius = 360f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.lights_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()

        lightsDetailsViewModel.colorPickerEventListener = this

        lightsDetailsViewModel.toastMessage.observe(this, Observer {
            if (it != null) {
                val message = getString(it)
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lights_details, container, false)
        binding.lifecycleOwner = this
        binding.lightDetailsViewModel = lightsDetailsViewModel

        binding.brightness.setDrawingCacheEnabled(true)
        brightnessBitmap = Bitmap.createBitmap(300, 100, Bitmap.Config.ARGB_8888)
        brightnessCanvas = Canvas(brightnessBitmap)

        brightnessView = BrightnessSeekBar((activity as SmartHomeActivity).applicationContext)
        brightnessView.draw(brightnessCanvas)

        binding.brightness.setImageBitmap(brightnessBitmap)

        binding.colorPicker.setDrawingCacheEnabled(true)
        val bitmap = Bitmap.createBitmap(imageQuality, imageQuality, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val colorPickerView = ColorPicker((activity as SmartHomeActivity).applicationContext)
        colorPickerView.draw(canvas)

        binding.colorPicker.setImageBitmap(bitmap)

        return binding.root
    }

    override fun setBrightnessSeekBarColor(red: Int, green: Int, blue: Int) {
        brightnessView.brightnessPaint.color = Color.rgb(red, green, blue)
        brightnessView.draw(brightnessCanvas)
        binding.brightness.setImageBitmap(brightnessBitmap)
    }

    override fun setCurrentImageViewColor(red: Int, green: Int, blue: Int) {
        binding.currentColor.setBackgroundColor(Color.rgb(red, green, blue))
    }
}

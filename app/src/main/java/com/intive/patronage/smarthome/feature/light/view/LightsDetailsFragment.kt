package com.intive.patronage.smarthome.feature.light.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
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
import kotlin.math.PI
import kotlin.math.sin

class LightsDetailsFragment : Fragment(), ColorPickerEventListener {

    private lateinit var binding: FragmentLightsDetailsBinding
    private val lightsDetailsViewModel by viewModel<LightsDetailsViewModel> {
        parametersOf(this ,this.arguments?.getInt("ID"))
    }

    private lateinit var brightnessView: BrightnessBar
    private lateinit var brightnessBitmap: Bitmap
    private lateinit var brightnessCanvas: Canvas

    private val colorPickerPointer = ColorPickerPointer()
    private val brightnessBarPointer = BrightnessBarPointer()

    private val imageQuality = 720

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
        binding.colorPicker.setDrawingCacheEnabled(true)

        brightnessBitmap = Bitmap.createBitmap(imageQuality / 10 * 3, imageQuality / 10, Bitmap.Config.ARGB_8888)
        val colorPickerBitmap = Bitmap.createBitmap(imageQuality, imageQuality, Bitmap.Config.ARGB_8888)

        brightnessCanvas = Canvas(brightnessBitmap)
        val colorPickerCanvas = Canvas(colorPickerBitmap)

        brightnessView = BrightnessBar((activity as SmartHomeActivity).applicationContext)
        val colorPickerView = ColorPicker((activity as SmartHomeActivity).applicationContext)

        brightnessView.draw(brightnessCanvas)
        colorPickerView.draw(colorPickerCanvas)

        binding.brightness.setImageBitmap(brightnessBitmap)
        binding.colorPicker.setImageBitmap(colorPickerBitmap)

        binding.brightness.viewTreeObserver.addOnGlobalLayoutListener {
            loadPointers()
        }

        val brightnessOverlay = binding.brightness.overlay
        val colorPickerOverlay = binding.colorPicker.overlay

        brightnessOverlay.add(brightnessBarPointer)
        colorPickerOverlay.add(colorPickerPointer)

        return binding.root
    }

    private fun calculatePointersPosition(hsv: IntArray) {
        val radius = if (binding.colorPicker.width < binding.colorPicker.height) {
            binding.colorPicker.width / 2
        } else {
            binding.colorPicker.height / 2
        }

        val angleInRadians: Float = (hsv[0].toFloat() * PI.toFloat()) / 180
        val partOfRadius: Float = (hsv[1].toFloat() / 100) * radius

        val y = partOfRadius * sin(angleInRadians)
        val x = partOfRadius * sin((PI / 2) - angleInRadians).toFloat()

        lightsDetailsViewModel.colorPickerEventListener.setColorPickerPointerPosition(
            (binding.colorPicker.width / 2) + x,
            (binding.colorPicker.height / 2) - y
        )

        lightsDetailsViewModel.colorPickerEventListener.setBrightnessBarPointerPosition(
            (binding.brightness.width.toFloat() / 100) * hsv[2]
        )

        lightsDetailsViewModel.brightnessBarPointerX = (binding.brightness.width.toFloat() / 100) * hsv[2]
    }

    private fun loadPointers() {
        brightnessBarPointer.height = binding.brightness.height.toFloat()
        lightsDetailsViewModel.brightnessBarPointerEndX = binding.brightness.width.toFloat()

        lightsDetailsViewModel.hsv.observe(this, Observer {
            if (it != null && lightsDetailsViewModel.brightnessBarPointerX == 0f) {
                calculatePointersPosition(it)
            }
        })
    }

    override fun setBrightnessBarColor(red: Int, green: Int, blue: Int) {
        brightnessView.brightnessPaint.color = Color.rgb(red, green, blue)
        brightnessView.draw(brightnessCanvas)
        binding.brightness.setImageBitmap(brightnessBitmap)
    }

    override fun setCurrentImageViewColor(red: Int, green: Int, blue: Int) {
        binding.currentColor.setBackgroundColor(Color.rgb(red, green, blue))
    }

    override fun setColorPickerPointerPosition(x: Float, y: Float) {
        colorPickerPointer.x = x
        colorPickerPointer.y = y
        colorPickerPointer.invalidateSelf()
    }

    override fun setBrightnessBarPointerPosition(x: Float) {
        val margin = 18f

        if (x >= lightsDetailsViewModel.brightnessBarPointerEndX - margin) {
            brightnessBarPointer.x = lightsDetailsViewModel.brightnessBarPointerEndX - margin
        } else if (x <= margin) {
            brightnessBarPointer.x = margin
        } else {
            brightnessBarPointer.x = x
        }

        brightnessBarPointer.invalidateSelf()
    }

    override fun resetPointersPosition() {
        lightsDetailsViewModel.hsv.observe(this, Observer {
            if (it != null) {
                calculatePointersPosition(it)
            }
        })
    }
}
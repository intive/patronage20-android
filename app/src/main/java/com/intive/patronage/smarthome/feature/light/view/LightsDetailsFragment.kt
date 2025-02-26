package com.intive.patronage.smarthome.feature.light.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
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

private const val IMAGE_QUALITY = 720

class LightsDetailsFragment : Fragment(), ColorPickerEventListener {

    private lateinit var binding: FragmentLightsDetailsBinding
    private val lightsDetailsViewModel by viewModel<LightsDetailsViewModel> {
        parametersOf(this ,this.arguments?.getInt("ID"))
    }

    private lateinit var brightnessView: BrightnessBar
    private lateinit var brightnessBitmap: Bitmap
    private lateinit var brightnessCanvas: Canvas
    private lateinit var colorPickerPointer: ColorPickerPointer
    private lateinit var brightnessBarPointer: BrightnessBarPointer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lightsDetailsViewModel.halfOfPointerWidth = resources.displayMetrics.density * 8
        lightsDetailsViewModel.colorPickerEventListener = this

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lights_details, container, false)
        binding.lifecycleOwner = this
        binding.lightDetailsViewModel = lightsDetailsViewModel

        setupToolbar()
        setupView()

        return binding.root
    }

    private fun setupView() {
        val pointerRadius = lightsDetailsViewModel.halfOfPointerWidth - resources.displayMetrics.density
        colorPickerPointer = ColorPickerPointer(pointerRadius)
        brightnessBarPointer = BrightnessBarPointer(pointerRadius - resources.displayMetrics.density)

        binding.brightness.setDrawingCacheEnabled(true)
        binding.colorPicker.setDrawingCacheEnabled(true)

        brightnessBitmap = Bitmap.createBitmap(IMAGE_QUALITY / 10 * 3, IMAGE_QUALITY / 10, Bitmap.Config.ARGB_8888)
        val colorPickerBitmap = Bitmap.createBitmap(IMAGE_QUALITY, IMAGE_QUALITY, Bitmap.Config.ARGB_8888)

        brightnessCanvas = Canvas(brightnessBitmap)
        val colorPickerCanvas = Canvas(colorPickerBitmap)

        brightnessView = BrightnessBar((activity as SmartHomeActivity).applicationContext)
        val colorPickerView = ColorPicker((activity as SmartHomeActivity).applicationContext)

        brightnessView.draw(brightnessCanvas)
        colorPickerView.draw(colorPickerCanvas)

        binding.brightness.setImageBitmap(brightnessBitmap)
        binding.colorPicker.setImageBitmap(colorPickerBitmap)

        binding.brightness.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    loadPointers()
                    binding.brightness.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        )

        val brightnessOverlay = binding.brightness.overlay
        val colorPickerOverlay = binding.colorPicker.overlay

        brightnessOverlay.add(brightnessBarPointer)
        colorPickerOverlay.add(colorPickerPointer)
    }

    private fun setupToolbar() {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.lights_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()
    }

    private fun calculatePointersPosition(hsv: IntArray, colorPickerPointerWasTouched: Boolean = true) {
        val radius = if (binding.colorPicker.width < binding.colorPicker.height) {
            binding.colorPicker.width / 2 - lightsDetailsViewModel.halfOfPointerWidth
        } else {
            binding.colorPicker.height / 2 - lightsDetailsViewModel.halfOfPointerWidth
        }

        val angleInRadians: Float = (hsv[0].toFloat() * PI.toFloat()) / 180
        val partOfRadius: Float = (hsv[1].toFloat() / 100) * radius

        val y = partOfRadius * sin(angleInRadians)
        val x = partOfRadius * sin((PI / 2) - angleInRadians).toFloat()

        if (colorPickerPointerWasTouched) {
            lightsDetailsViewModel.colorPickerEventListener.setColorPickerPointerPosition(
                (binding.colorPicker.width / 2) + x,
                (binding.colorPicker.height / 2) - y
            )
        }

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
                lightsDetailsViewModel.hsv.removeObservers(this)
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
        val margin = lightsDetailsViewModel.halfOfPointerWidth

        if (x >= lightsDetailsViewModel.brightnessBarPointerEndX - margin) {
            brightnessBarPointer.x = lightsDetailsViewModel.brightnessBarPointerEndX - margin
        } else if (x <= margin) {
            brightnessBarPointer.x = margin
        } else {
            brightnessBarPointer.x = x
        }

        brightnessBarPointer.invalidateSelf()
    }

    override fun resetPointersPosition(colorPickerPointerWasTouched: Boolean, hsv: IntArray) {
        calculatePointersPosition(hsv, colorPickerPointerWasTouched)
    }

    override fun showToast(message: Int) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
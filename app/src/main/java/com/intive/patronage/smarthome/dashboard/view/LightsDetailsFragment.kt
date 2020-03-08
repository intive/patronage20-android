package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.FragmentLightsDetailsBinding
import com.intive.patronage.smarthome.dashboard.viewmodel.LightsDetailsViewModel
import kotlinx.android.synthetic.main.fragment_lights_details.*
import kotlinx.android.synthetic.main.fragment_lights_details.view.*

class LightsDetailsFragment : Fragment() {

    private val lightDetailsViewModel by lazy { ViewModelProviders.of(this).get(
        LightsDetailsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.setTitle(R.string.lights_details_appbar)

        val binding: FragmentLightsDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lights_details, container, false)
        binding.lifecycleOwner = this
        binding.lightDetailsViewModel = lightDetailsViewModel
        val view = binding.root

        view.applyButton.setOnClickListener {
            val hsv = lightDetailsViewModel.onApplyClicked()
            Toast.makeText(activity, "hue: ${hsv[0]}\n saturation: ${hsv[1]}\n value: ${hsv[2]}", Toast.LENGTH_LONG).show()
        }

        view.resetButton.setOnClickListener {
            lightDetailsViewModel.onResetClicked()
        }

        view.redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                setCurrentColor(seek)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        view.greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                setCurrentColor(seek)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        view.blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                setCurrentColor(seek)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        return view
    }

    private fun setCurrentColor(seekBar: SeekBar) {
        val progress = seekBar.progress
        when(seekBar) {
            redSeekBar -> {
                lightDetailsViewModel.redValue = progress
                redValueTextView.text = progress.toString()
            }
            greenSeekBar -> {
                lightDetailsViewModel.greenValue = progress
                greenValueTextView.text = progress.toString()
            }
            blueSeekBar -> {
                lightDetailsViewModel.blueValue = progress
                blueValueTextView.text = progress.toString()
            }
        }
        colorView.setBackgroundColor(lightDetailsViewModel.setCurrentColor())
    }
}

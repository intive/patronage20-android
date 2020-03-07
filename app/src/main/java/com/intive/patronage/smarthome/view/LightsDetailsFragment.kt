package com.intive.patronage.smarthome.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.FragmentLightsDetailsBinding
import com.intive.patronage.smarthome.viewmodel.LightsDetailsViewModel
import kotlinx.android.synthetic.main.fragment_lights_details.*
import kotlinx.android.synthetic.main.fragment_lights_details.view.*
import kotlinx.android.synthetic.main.fragment_lights_details.view.redSeekBar

class LightsDetailsFragment : Fragment() {

    private val lightDetailsViewModel by lazy { ViewModelProviders.of(this).get(LightsDetailsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.setTitle(R.string.lights_details_appbar)

        val binding: FragmentLightsDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lights_details, container, false)
        binding.lifecycleOwner = this
        binding.lightDetailsViewModel = lightDetailsViewModel
        val view = binding.root

        view.okButton.setOnClickListener { lightDetailsViewModel.onOkClicked() }
        view.cancelButton.setOnClickListener { lightDetailsViewModel.onCancelClicked() }

        view.redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                setCurrentColor(redSeekBar, progress)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        view.greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                setCurrentColor(greenSeekBar, progress)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        view.blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                setCurrentColor(blueSeekBar, progress)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        return view
    }

    fun setCurrentColor(seekBar: SeekBar, progress: Int) {
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

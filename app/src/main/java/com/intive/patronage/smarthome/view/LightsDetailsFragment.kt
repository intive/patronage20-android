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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.FragmentLightsDetailsBinding
import com.intive.patronage.smarthome.viewmodel.LightsDetailsViewModel
import kotlinx.android.synthetic.main.fragment_lights_details.*
import kotlinx.android.synthetic.main.fragment_lights_details.view.*
import org.koin.android.viewmodel.ext.android.viewModel

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

        view.cancelButton.setOnClickListener {
            //TODO: show previous state
        }

        view.redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                lightDetailsViewModel.redValue = progress
                view.redValueTextView.text = progress.toString()
                setCurrentColor()
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        view.greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                lightDetailsViewModel.greenValue = progress
                view.greenValueTextView.text = progress.toString()
                setCurrentColor()
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        view.blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                lightDetailsViewModel.blueValue = progress
                view.blueValueTextView.text = progress.toString()
                setCurrentColor()
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        return view
    }

    fun setCurrentColor() {
        val color = Color.rgb(lightDetailsViewModel.redValue, lightDetailsViewModel.greenValue, lightDetailsViewModel.blueValue)
        colorView.setBackgroundColor(color)
    }
}

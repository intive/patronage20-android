package com.intive.patronage.smarthome.feature.hvac.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.SmartHomeAlertDialog
import com.intive.patronage.smarthome.databinding.FragmentHvacDetailsBinding
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.hvac.viewmodel.HVACViewEventListener
import com.intive.patronage.smarthome.feature.hvac.viewmodel.HvacViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HvacDetailsFragment : Fragment(), HVACViewEventListener {

    private val hvacViewModel by viewModel<HvacViewModel> {
        parametersOf(this, this.arguments?.getInt("ID"))
    }
    lateinit var binding: FragmentHvacDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.hvac_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hvac_details, container, false)
        binding.lifecycleOwner = this
        binding.hvacViewModel = hvacViewModel
        hvacViewModel.hvacViewEventListener = this
        return binding.root
    }

    override fun setTemperature(temperature: Float) {
        binding.hvacCircle.temperatureFloat = temperature
        binding.hvacCircle.postInvalidate()
    }

    override fun setHysteresis(hysteresis: Int) {
        binding.hvacCircle.hysteresis = hysteresis
        binding.hvacCircle.postInvalidate()
    }

    override fun connectionError(error: Boolean) {
        if (error) {
            SmartHomeAlertDialog().showSmartHomeDialog(
                activity as Activity, R.string.error_title
                , R.string.hvac_details_connecting_error
            )
            { activity?.onBackPressed() }
        }
    }

    override fun setHeatingTemperature(heatingTemperature: Int) {
        binding.hvacCircle.minTemperature = heatingTemperature
        binding.hvacCircle.postInvalidate()
    }

    override fun setCoolingTemperature(coolingTemperature: Int) {
        binding.hvacCircle.maxTemperature = coolingTemperature
        binding.hvacCircle.postInvalidate()
    }

    override fun saveSetting() {
        Toast.makeText(activity, R.string.apply_toast, Toast.LENGTH_SHORT).show()
    }

    override fun resetSetting() {
        binding.hvacCircle.postInvalidate()
    }
}
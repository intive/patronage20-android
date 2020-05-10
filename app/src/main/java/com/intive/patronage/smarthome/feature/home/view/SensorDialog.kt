package com.intive.patronage.smarthome.feature.home.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.percentToCoordinateX
import com.intive.patronage.smarthome.common.percentToCoordinateY
import com.intive.patronage.smarthome.databinding.SensorDialogFragmentBinding
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.MapPosition
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.viewmodel.HomeSharedViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class SensorDialog : DialogFragment() {
    private val sensorDialogListAdapter: SensorDialogListAdapter by inject {
        parametersOf(::onItemClick)
    }
    private val dialogViewModel: HomeSharedViewModel by sharedViewModel()

    private lateinit var image: HomeLayoutView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SensorDialogFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.sensor_dialog_fragment, container, false)
        binding.lifecycleOwner = this
        binding.homeViewModelDataBind = dialogViewModel
        setupRecyclerView(binding)
        image = activity!!.findViewById(R.id.home)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        window?.setGravity(Gravity.CENTER)
    }

    private fun setupRecyclerView(binding: SensorDialogFragmentBinding) {
        val recyclerView: RecyclerView = binding.sensorRecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = sensorDialogListAdapter
        }
    }

    private fun onItemClick(sensor: DashboardSensor) {
        if (sensor.mapPosition != null) {
            dialogViewModel.deleteSensor(sensor.id.toInt())
        } else {
            val x = dialogViewModel.getSensorXPosition()
            val y = dialogViewModel.getSensorYPosition()
            if (image.checkForSensors(
                    percentToCoordinateX(x, image.width),
                    percentToCoordinateY(y, image.height)
                )
            ) {
                dialogViewModel.postSensor(
                    sensor.id.toInt(),
                    HomeSensor(sensor.id.toInt(), sensor.type, MapPosition(x, y))
                )
            } else {
                Toast.makeText(
                    this.context,
                    getString(R.string.sensor_add_failure),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dialog?.dismiss()
    }


}

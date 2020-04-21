package com.intive.patronage.smarthome.feature.home.view

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.coordinateToPercentX
import com.intive.patronage.smarthome.common.coordinateToPercentY
import com.intive.patronage.smarthome.common.percentToCoordinateX
import com.intive.patronage.smarthome.common.percentToCoordinateY
import com.intive.patronage.smarthome.databinding.SensorDialogFragmentBinding
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.viewmodel.SensorDialogViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SensorDialog : DialogFragment() {
    private val sensorDialogListAdapter: SensorDialogListAdapter by inject {
        parametersOf(::onItemClick)
    }
    private val dialogViewModel: SensorDialogViewModel by viewModel()

    private lateinit var image: HomeLayoutView
    private var actualSensorX = 0f
    private var actualSensorY = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SensorDialogFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.sensor_dialog_fragment, container, false)
        if (savedInstanceState != null) {
            actualSensorX = savedInstanceState.getFloat("SENSOR_X")
            actualSensorY = savedInstanceState.getFloat("SENSOR_Y")
        }
        binding.lifecycleOwner = this
        binding.homeViewModelDataBind = dialogViewModel
        setupRecyclerView(binding)
        image = activity!!.findViewById(R.id.home)
        return binding.root
    }

    fun setSensorPosition(x: Float, y: Float, imageWidth: Int, imageHeight: Int) {
        actualSensorX = coordinateToPercentX(x, imageWidth)
        actualSensorY = coordinateToPercentY(y, imageHeight)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putFloat("SENSOR_X", actualSensorX)
        outState.putFloat("SENSOR_Y", actualSensorY)
        super.onSaveInstanceState(outState)
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

    private fun onItemClick(sensor: HomeSensor) {
        if (sensor.mapPosition != null) {
            //TODO: Remove sensor API call
//            image.removeSensor(
//                percentToCoordinateX(sensor.mapPosition.x.toFloat(), image.width),
//                percentToCoordinateY(sensor.mapPosition.y.toFloat(), image.height)
//            )
        } else {
            if (image.addSensor(percentToCoordinateX(actualSensorX, image.width), percentToCoordinateY(actualSensorY, image.height), sensor.type)) {
                //TODO: Add sesnor API call
//                sensor.x = actualSensorX
//                sensor.y = actualSensorY
            }
        }
        dialog?.dismiss()
    }


}

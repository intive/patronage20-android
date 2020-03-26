package com.intive.patronage.smarthome.feature.home.view

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.annotation.ContentView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.SensorDialogFragmentBinding
import com.intive.patronage.smarthome.feature.home.viewmodel.SensorDialogViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.sensor_list_item.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SensorDialog: DialogFragment() {
    private val sensorDialogListAdapter: SensorDialogListAdapter by inject {
        parametersOf(::onItemClick)
    }
    private val dialogViewModel: SensorDialogViewModel by viewModel()

    private var x = 0f
    private var y = 0f

    fun setPosition(x:Float, y:Float){
        this.x = x
        this.y = y
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SensorDialogFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.sensor_dialog_fragment, container,  false)
        binding.lifecycleOwner = this
        binding.homeViewModelDataBind = dialogViewModel
        setupRecyclerView(binding)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        window?.setGravity(Gravity.TOP + Gravity.START)
        val params = window?.attributes
        Log.d("XD", params!!.width.toString())
        val orientation = resources.configuration.orientation
        params?.x = this.x.toInt()
        params?.y = this.y.toInt()
        window?.attributes = params
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            window?.setLayout(displayMetrics.widthPixels/2, displayMetrics.heightPixels/2)
        }
        else {
            window?.setLayout(displayMetrics.widthPixels/4, displayMetrics.heightPixels/2)
        }
    }

    private fun setupRecyclerView(binding: SensorDialogFragmentBinding) {
        val recyclerView: RecyclerView = binding.sensorRecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = sensorDialogListAdapter
        }
    }

    fun onItemClick(){

    }
}
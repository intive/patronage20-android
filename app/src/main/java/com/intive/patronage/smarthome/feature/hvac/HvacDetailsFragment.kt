package com.intive.patronage.smarthome.feature.hvac

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.model.HVACRoom
import kotlinx.android.synthetic.main.fragment_hvac_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class HvacDetailsFragment : Fragment() {

    private val hvacViewModel: HvacViewModel by viewModel()
     var hvacListGood: MutableList<HVACRoom> = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.hvac_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)

      //  Log.d("testowanie", hvacViewModel.hvacList.toString())
        //obserwacja()
        /*val binding: FragmentHvacDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hvac_details,container,false)
         binding.lifecycleOwner = this
         binding.hvacViewModel = hvacViewModel

       return binding.root*/
        hvacViewModel.loadHvac()

        val list = hvacViewModel.getList()
       // Log.d("testowanie frag", list.toString())

        return inflater.inflate(R.layout.fragment_hvac_details, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val hvacCircle = hvac_circle
        temp_up.setOnClickListener { hvacCircle.teperaturaDodanie() }
        temp_down.setOnClickListener { hvacCircle.temperaturaMinus() }
        hist_up.setOnClickListener { hvacCircle.histUp() }
        hist_down.setOnClickListener { hvacCircle.histDown() }
    }

    /*fun obserwacja() {
        hvacViewModel.getList().observe(this, Observer { newItem ->
            Log.d("testowanie po newItem", newItem.toString())
            hvacListGood.addAll(newItem)})
    }*/
    fun wyswietl(lista:List<*>){
        Log.d("testowanie tutaj", lista.toString())
    }
}
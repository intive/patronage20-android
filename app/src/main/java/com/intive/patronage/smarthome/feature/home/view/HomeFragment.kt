package com.intive.patronage.smarthome.feature.home.view

import android.os.Bundle
import androidx.lifecycle.Observer
import android.view.*
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.home.viewmodel.SensorDialogViewModel

import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val dialogViewModel: SensorDialogViewModel by viewModel()
    lateinit var image: HomeLayoutView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.home_fragment, container, false)
        image = myView.findViewById(R.id.home)
        image.create(fragmentManager!!)
        dialogViewModel.items.observe(this, Observer {
            if (it != null) {
                image.setData(it)
            }
        })
        return myView
    }

}

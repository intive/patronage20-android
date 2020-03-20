package com.intive.patronage.smarthome.feature.blind.view

import android.util.Log
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.blind.viewmodel.BlindDetailsViewModel

@BindingAdapter("onLayoutChange")
fun setOnLayoutChangeListener(view: BlindView, blindDetailsViewModel: BlindDetailsViewModel) {
    view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        val maxPosition: Float = view.height.toFloat() - view.height.toFloat() / 5 - 15F
        val onePercent: Float = maxPosition / 100
        view.position = onePercent * blindDetailsViewModel.position.toFloat()
    }
}
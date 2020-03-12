package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment

class FragmentEvent(clazz: Class<*>, bundle: Bundle? = null, val containerId: Int) : NavigationEvent(clazz, bundle) {

    fun buildFragment() : Fragment {
        return (clazz.getConstructor().newInstance() as Fragment).apply {
            bundle?.let {
                this.arguments = bundle
            }
        }
    }
}
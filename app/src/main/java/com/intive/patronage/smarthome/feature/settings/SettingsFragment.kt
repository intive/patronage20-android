package com.intive.patronage.smarthome.feature.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.PreferencesWrapper
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.view.settingsRecyclerView
import kotlinx.android.synthetic.main.settings_list_item.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SettingsFragment : Fragment() {
    private val settingsListAdapter: SettingsListAdapter by inject {
        parametersOf(::onSettingClickListener)
    }
    private val dashboardCoordinator: DashboardCoordinator by inject {
        parametersOf(activity)
    }
    private val preferences: PreferencesWrapper by inject {
        parametersOf(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.settings_toolbar)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()

        val view = inflater.inflate(R.layout.settings_fragment, container, false)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.settingsRecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = settingsListAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    setupDarkModeSwitch(preferences, darkModeSwitch, resources)

                    notificationsSwitch.isChecked = true
                    notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            Log.d("switch.isChecked", "TRUE")
                        } else {
                            Log.d("switch.isChecked", "FALSE")
                        }
                    }

                    recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
    }

    private fun onSettingClickListener(settingType: SettingType) {
        context?.let { context ->
            SettingClickEvent.values().find {
                settingType.toString() == it.toString()
            }?.onClick(settingsRecyclerView, dashboardCoordinator, context)
        }
    }
}
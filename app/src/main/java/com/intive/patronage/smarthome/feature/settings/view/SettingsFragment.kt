package com.intive.patronage.smarthome.feature.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.intive.patronage.smarthome.BuildConfig
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.PreferencesWrapper
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.settings.SettingClickEvent
import com.intive.patronage.smarthome.feature.settings.SettingType
import com.intive.patronage.smarthome.feature.settings.setupDarkModeSwitch
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.settings_fragment.view.settingsRecyclerView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SettingsFragment : Fragment() {
    private val settingsListAdapter: SettingsListAdapter by inject {
        parametersOf(filterItemList(), ::onSettingClickListener)
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
        setupToolbar()
        setupDarkModeSwitch(resources)

        val view = inflater.inflate(R.layout.settings_fragment, container, false)
        setupRecyclerView(view)
        return view
    }

    private fun filterItemList() = if (BuildConfig.DEBUG) {
        SettingType.values()
    } else {
        SettingType.values().filter { !it.onlyDebug }.toTypedArray()
    }

    private fun setupToolbar() {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.settings_toolbar)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.settingsRecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = settingsListAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun onSettingClickListener(settingType: SettingType, itemView: View) {
        SettingClickEvent.values().find {
            settingType.toString() == it.toString()
        }?.onClick(itemView, dashboardCoordinator, preferences)
    }
}
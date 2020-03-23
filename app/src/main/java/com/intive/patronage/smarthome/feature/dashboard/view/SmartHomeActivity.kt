package com.intive.patronage.smarthome.feature.dashboard.view

import android.os.Bundle
import android.util.Log
import android.view.View.SYSTEM_UI_FLAG_VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SmartHomeActivity : AppCompatActivity() {

    private val dashboardCoordinator: DashboardCoordinator by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_home_activity)

        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            dashboardCoordinator.goToDashboard()
        }

        val dashboardService = get<DashboardService>()
        dashboardService.getDashboard()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("DASHBOARD_SERVICE", it.toString())
            }, {
                Log.d("DASHBOARD_SERVICE", it.toString())
            })

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupTabs()

    }

    override fun onBackPressed() {
        dashboardCoordinator.goBack()
    }

    fun setupTabs() {
//        val pagerAdapter = DashboardViewPagerAdapter(supportFragmentManager)
//        val viewPager = findViewById<ViewPager2>(R.id.dashboardViewPager)
//        val tabView = findViewById<TabLayout>(R.id.dashboardTabLayout)
//        viewPager.adapter = pagerAdapter
//        tabView.setupWithViewPager(viewPager)

        val pagerAdapter = DashboardViewPagerAdapter(supportFragmentManager, lifecycle)
        pagerAdapter.addFragment(DashboardFragment(), "Dashboard")
        val viewPager = findViewById<ViewPager2>(R.id.dashboardViewPager)
        viewPager.adapter = pagerAdapter

    }



}
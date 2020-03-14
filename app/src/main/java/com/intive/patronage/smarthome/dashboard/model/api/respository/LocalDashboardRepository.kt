package com.intive.patronage.smarthome.dashboard.model.api.respository

import com.intive.patronage.smarthome.dashboard.model.Dashboard
import io.reactivex.Maybe

class LocalDashboardRepository : DashboardRepositoryAPI {

    private lateinit var dashboard: Dashboard

    override fun getDashboard(): Maybe<Dashboard> = if (::dashboard.isInitialized) {
        Maybe.just(dashboard)
    } else {
        Maybe.empty<Dashboard>()
    }


    override fun setDashboard(dashboard: Dashboard) {
        this.dashboard = dashboard
    }
}
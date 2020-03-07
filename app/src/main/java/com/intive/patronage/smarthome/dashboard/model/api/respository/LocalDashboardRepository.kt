package com.intive.patronage.smarthome.dashboard.model.api.respository

import com.intive.patronage.smarthome.dashboard.model.Dashboard
import io.reactivex.Maybe

class LocalDashboardRepository : DashboardRepositoryAPI {

    private lateinit var dashboard: Dashboard

    override fun getDashboard(): Maybe<Dashboard> = Maybe.just(dashboard)

    override fun setDashboard(dashboard: Dashboard) {
        this.dashboard = dashboard
    }
}
package com.intive.patronage.smarthome.dashboard.model.api.respository

import com.intive.patronage.smarthome.dashboard.model.Dashboard
import io.reactivex.Maybe

interface DashboardRepositoryAPI {
    fun getDashboard(): Maybe<Dashboard>
    fun setDashboard(dashboard: Dashboard)
}
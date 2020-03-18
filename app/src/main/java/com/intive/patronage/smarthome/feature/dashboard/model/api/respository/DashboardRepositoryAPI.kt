package com.intive.patronage.smarthome.feature.dashboard.model.api.respository

import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import io.reactivex.Maybe

interface DashboardRepositoryAPI {
    fun getDashboard(): Maybe<Dashboard>
    fun setDashboard(dashboard: Dashboard)
}
package com.intive.patronage.smarthome.feature.dashboard.model.api.room.repository

import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import io.reactivex.Maybe
import io.reactivex.Observable

interface DashboardRoomRepositoryAPI {
    fun getDashboardById(id: Long): Maybe<Dashboard>
    fun getAllDashboards(): Observable<List<Dashboard>>
    fun insertDashboard(dashboard: Dashboard)
    fun updateDashboard(dashboard: Dashboard)
    fun deleteDashboard(dashboard: Dashboard)
    fun deleteAllDashboards()
}
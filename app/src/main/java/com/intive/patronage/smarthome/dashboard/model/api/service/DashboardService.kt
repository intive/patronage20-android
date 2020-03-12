package com.intive.patronage.smarthome.dashboard.model.api.service

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.dashboard.model.Dashboard
import com.intive.patronage.smarthome.dashboard.model.api.respository.DashboardRepositoryAPI
import io.reactivex.Single

class DashboardService(
    private val smartHomeAPI: SmartHomeAPI,
    private val dashboardRepository: DashboardRepositoryAPI
) {

    fun getDashboard(): Single<Dashboard> = dashboardRepository.getDashboard()
        .switchIfEmpty(getDashboardFromNetwork())

    private fun getDashboardFromNetwork(): Single<Dashboard> {
        return smartHomeAPI.getDashboard().doOnSuccess {
            dashboardRepository.setDashboard(it)
        }
    }

}
package com.intive.patronage.smarthome.feature.dashboard.model.api.room.repository

import android.util.Log
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.dao.DashboardDao
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val DASHBOARD_ROOM_LOG_TAG = "DASHBOARD_ROOM"

class DashboardRoomRepository(private val dashboardDao: DashboardDao) : DashboardRoomRepositoryAPI {
    override fun getDashboardById(id: Long): Maybe<Dashboard> = dashboardDao.getDashboardById(id)

    override fun getAllDashboards(): Observable<List<Dashboard>> = dashboardDao.getAllDashboards()

    override fun insertDashboard(dashboard: Dashboard) {
        dashboardDao.insertDashboard(dashboard)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(DASHBOARD_ROOM_LOG_TAG, "Insert Success") },
                { Log.d(DASHBOARD_ROOM_LOG_TAG, "Insert Failed, $it") }
            )
    }

    override fun updateDashboard(dashboard: Dashboard) {
        Completable.fromAction { dashboardDao.updateDashboard(dashboard) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(DASHBOARD_ROOM_LOG_TAG, "Update Success") },
                { Log.d(DASHBOARD_ROOM_LOG_TAG, "Update Failed, $it") }
            )
    }

    override fun deleteDashboard(dashboard: Dashboard) {
        Completable.fromAction { dashboardDao.deleteDashboard(dashboard) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(DASHBOARD_ROOM_LOG_TAG, "Delete Success") },
                { Log.d(DASHBOARD_ROOM_LOG_TAG, "Delete Failed, $it") }
            )
    }

    override fun deleteAllDashboards() {
        Completable.fromAction { dashboardDao.deleteAllDashboards() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(DASHBOARD_ROOM_LOG_TAG, "Delete All Success") },
                { Log.d(DASHBOARD_ROOM_LOG_TAG, "Delete All Failed, $it") }
            )
    }
}
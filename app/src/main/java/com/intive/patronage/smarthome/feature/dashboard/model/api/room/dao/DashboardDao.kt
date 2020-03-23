package com.intive.patronage.smarthome.feature.dashboard.model.api.room.dao

import androidx.room.*
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface DashboardDao {

    @Query("SELECT * FROM dashboard_table WHERE dashboard_id = :id")
    fun getDashboardById(id: Long): Maybe<Dashboard>

    @Query("SELECT * FROM dashboard_table")
    fun getAllDashboards(): Observable<List<Dashboard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDashboard(dashboard: Dashboard): Completable

    @Update
    fun updateDashboard(dashboard: Dashboard): Completable

    @Delete
    fun deleteDashboard(dashboard: Dashboard): Completable

    @Query("DELETE FROM dashboard_table")
    fun deleteAllDashboards()
}
package com.intive.patronage.smarthome.feature.dashboard.model.api.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.dao.DashboardDao

@Database(entities = [Dashboard::class], version = 2, exportSchema = false)
@TypeConverters(DashboardTypeConverters::class)
abstract class DashboardDatabase: RoomDatabase() {
    abstract fun dashboardDao(): DashboardDao
}
package com.intive.patronage.smarthome.feature.dashboard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "dashboard_table")
data class Dashboard(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "dashboard_id") val id: Long,
    @Json(name = "temperatureSensors") val temperatureSensors: List<TemperatureSensor>,
    @Json(name = "windowSensors") val windowSensors: List<WindowSensor>,
    @Json(name = "windowBlinds") val windowBlinds: List<WindowBlind>,
    @Json(name = "RFIDSensors") val RFIDSensors: List<RFIDSensor>,
    @Json(name = "smokeSensors") val smokeSensors: List<SmokeSensor>,
    @Json(name = "HVACStatus") val HVACStatus: HVACStatus,
    @Json(name = "HVACRooms") val HVACRooms: List<HVACRoom>,
    @Json(name = "lights") val lights: List<Light>
)
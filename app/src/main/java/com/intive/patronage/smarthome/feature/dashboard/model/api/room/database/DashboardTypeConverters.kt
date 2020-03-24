package com.intive.patronage.smarthome.feature.dashboard.model.api.room.database

import androidx.room.TypeConverter
import com.intive.patronage.smarthome.feature.dashboard.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class DashboardTypeConverters {
    companion object {
        private val moshi = Moshi.Builder().build()

        private val temperatureSensorAdapter = moshi.adapter<List<TemperatureSensor>>(
            Types.newParameterizedType(List::class.java, TemperatureSensor::class.java)
        )
        private val windowSensorsAdapter = moshi.adapter<List<WindowSensor>>(
            Types.newParameterizedType(List::class.java, WindowSensor::class.java)
        )
        private val windowBlindsAdapter = moshi.adapter<List<WindowBlind>>(
            Types.newParameterizedType(List::class.java, WindowBlind::class.java)
        )
        private val RFIDSensorsAdapter = moshi.adapter<List<RFIDSensor>>(
            Types.newParameterizedType(List::class.java, RFIDSensor::class.java)
        )
        private val smokeSensorsAdapter = moshi.adapter<List<SmokeSensor>>(
            Types.newParameterizedType(List::class.java, SmokeSensor::class.java)
        )
        private val HVACStatusAdapter = moshi.adapter<HVACStatus>(
            Types.getRawType(HVACStatus::class.java)
        )
        private val HVACRoomsAdapter = moshi.adapter<List<HVACRoom>>(
            Types.newParameterizedType(List::class.java, HVACRoom::class.java)
        )
        private val lightsAdapter = moshi.adapter<List<Light>>(
            Types.newParameterizedType(List::class.java, Light::class.java)
        )

        @JvmStatic
        @TypeConverter
        fun convertTemperatureSensorToJson(input: List<TemperatureSensor>?): String =
            temperatureSensorAdapter.toJson(input)

        @JvmStatic
        @TypeConverter
        fun convertJsonToTemperatureSensor(input: String): List<TemperatureSensor>? =
            temperatureSensorAdapter.fromJson(input)

        @JvmStatic
        @TypeConverter
        fun convertWindowSensorToJson(input: List<WindowSensor>?): String =
            windowSensorsAdapter.toJson(input)

        @JvmStatic
        @TypeConverter
        fun convertJsonToWindowSensor(input: String): List<WindowSensor>? =
            windowSensorsAdapter.fromJson(input)

        @JvmStatic
        @TypeConverter
        fun convertWindowBlindToJson(input: List<WindowBlind>?): String =
            windowBlindsAdapter.toJson(input)

        @JvmStatic
        @TypeConverter
        fun convertJsonToWindowBlind(input: String): List<WindowBlind>? =
            windowBlindsAdapter.fromJson(input)

        @JvmStatic
        @TypeConverter
        fun convertRFIDSensorToJson(input: List<RFIDSensor>?): String =
            RFIDSensorsAdapter.toJson(input)

        @JvmStatic
        @TypeConverter
        fun convertJsonToRFIDSensor(input: String): List<RFIDSensor>? =
            RFIDSensorsAdapter.fromJson(input)

        @JvmStatic
        @TypeConverter
        fun smokeSensorToJson(input: List<SmokeSensor>?): String =
            smokeSensorsAdapter.toJson(input)

        @JvmStatic
        @TypeConverter
        fun convertJsonToSmokeSensor(input: String): List<SmokeSensor>? =
            smokeSensorsAdapter.fromJson(input)

        @JvmStatic
        @TypeConverter
        fun convertHVACStatusToJson(input: HVACStatus?): String =
            HVACStatusAdapter.toJson(input)

        @JvmStatic
        @TypeConverter
        fun convertJsonToHVACStatus(input: String): HVACStatus? =
            HVACStatusAdapter.fromJson(input)

        @JvmStatic
        @TypeConverter
        fun convertHVACRoomsToJson(input: List<HVACRoom>?): String =
            HVACRoomsAdapter.toJson(input)

        @JvmStatic
        @TypeConverter
        fun convertJsonToHVACRooms(input: String): List<HVACRoom>? =
            HVACRoomsAdapter.fromJson(input)

        @JvmStatic
        @TypeConverter
        fun convertLightsToJson(input: List<Light>?): String =
            lightsAdapter.toJson(input)

        @JvmStatic
        @TypeConverter
        fun convertJsonToLights(input: String): List<Light>? =
            lightsAdapter.fromJson(input)
    }
}
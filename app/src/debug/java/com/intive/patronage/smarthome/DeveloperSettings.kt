package com.intive.patronage.smarthome

import com.intive.patronage.smarthome.BuildConfig

class DeveloperSettings {

    fun getVersion() = BuildConfig.VERSION_NAME

    fun isDebugMode() = BuildConfig.DEBUG
}
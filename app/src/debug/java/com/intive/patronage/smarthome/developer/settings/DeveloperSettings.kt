package com.intive.patronage.smarthome.developer.settings

import com.intive.patronage.smarthome.BuildConfig

class DeveloperSettings {

    fun getVersion() = BuildConfig.VERSION_NAME

    fun isDebugMode() = BuildConfig.DEBUG
}
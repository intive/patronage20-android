package com.intive.patronage.smarthome

class DeveloperSettings {

    fun getVersion() = BuildConfig.VERSION_NAME

    fun isDebugMode() = BuildConfig.DEBUG
}
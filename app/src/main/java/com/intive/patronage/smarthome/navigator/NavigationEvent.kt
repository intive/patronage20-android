package com.intive.patronage.smarthome.navigator

import android.os.Bundle

abstract class NavigationEvent(val clazz: Class<*>, val bundle: Bundle? = null)
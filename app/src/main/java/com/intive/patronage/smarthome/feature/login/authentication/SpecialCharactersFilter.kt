package com.intive.patronage.smarthome.feature.login.authentication

import android.content.res.Resources
import com.intive.patronage.smarthome.R
import java.util.regex.Pattern

fun findIllegalCharacters(text: CharSequence?, resources: Resources): String {
    val pattern = Pattern.compile("[\\u003C\\u003E\\u003B\\u005C\\u0020\\u0027\\u007B\\u007D\\u002F\\u007E]")

    text?.let {
        if(pattern.matcher(it).find()) {
            return resources.getString(R.string.illegal_character)
        }
    }
    return ""
}
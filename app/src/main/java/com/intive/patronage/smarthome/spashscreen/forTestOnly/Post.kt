package com.intive.patronage.smarthome.spashscreen.forTestOnly

import com.squareup.moshi.Json

data class Post(
    @Json(name = "userID")val userId: Int,
    @Json(name = "id")val id:Int,
    @Json(name = "title")val title:String,
    @Json(name = "body")val body:String)
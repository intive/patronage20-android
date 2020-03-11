package com.intive.patronage.smarthome.splashscreen.forTestOnly

import io.reactivex.Flowable
import retrofit2.http.GET

interface PostApi {

    @GET("/posts")
    fun getPosts(): Flowable<List<Post>>
}
package com.intive.patronage.smarthome.spashscreen.forTestOnly



import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class PostFetcher  {

    fun fetchPosts(): PostApi {

        val postRetrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .callFactory(OkHttpClient.Builder().build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return postRetrofit.create(PostApi::class.java)
    }


}
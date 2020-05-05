package com.intive.patronage.smarthome.feature.dashboard.model.api.authorization

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthorizationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("Cookie", "secret_cookie=3241231213fsdj23kj4kl32j4")
            .build()

        return chain.proceed(request)
    }
}
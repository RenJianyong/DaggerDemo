package com.rjy.http.retrofit.interceptor

import com.rjy.http.retrofit.body.JsResponseBody
import com.rjy.http.retrofit.callback.LoadCallback
import okhttp3.Interceptor
import okhttp3.Response

class JsDownloadInterceptor constructor(val downloadCallback: LoadCallback): Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val response = chain!!.proceed(chain!!.request())
        return response.newBuilder().body(JsResponseBody(response.body(), downloadCallback)).build()
    }
}
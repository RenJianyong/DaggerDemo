package com.rjy.http.retrofit

import com.rjy.http.entity.MessageEntity
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.*
import rx.Observable

interface HttpService {

//    @HTTP(method="POST",path="{mUrl}")
    @POST()
    fun post(@HeaderMap headerMap: Map<String,String>, @Url url:String, @Body requestBody: RequestBody):Observable<String>
}
package com.rjy.http.retrofit

import com.rjy.http.entity.MessageEntity
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.*
import rx.Observable

interface HttpService {

//    @HTTP(method="POST",path="{mUrl}")
    @POST()
    fun <T:MessageEntity> post(@Url url:String, @Body requestBody: RequestBody):Observable<T>

    @GET
    fun download(@Url url: String):Observable<ResponseBody>
}
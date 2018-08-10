package com.rjy.http.retrofit

import com.google.gson.Gson
import com.rjy.http.entity.MessageEntity
import com.rjy.http.ext.execute
import com.rjy.http.retrofit.callback.LoadCallback
import com.rjy.http.retrofit.callback.HttpCallback
import com.rjy.http.rx.BaseSuscriber
import okhttp3.MediaType
import okhttp3.RequestBody
import rx.Observable
import rx.functions.Func1
import java.io.File

object HttpClientUtil {

    fun <T:MessageEntity> post(url:String,params:Map<String,String>,httpCallback: HttpCallback<T>){
        //post请求
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),getParamStr(params))
        register().post<T>(url,requestBody)
                .execute(object : BaseSuscriber<T>(){
                    override fun onNext(t: T) {
                        super.onNext(t)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                    }

                    override fun onError(e: Throwable?) {
                        super.onError(e)
                    }
                })
    }

    fun <T> get(url: String, params: Map<String, String>, httpCallback: HttpCallback<T>){
        //get请求
    }

    fun download(url: String, params: Map<String, String>, loadCallback: LoadCallback){
        //下载
    }

    fun upload(url: String, params: Map<String, String>,file: File,loadCallback: LoadCallback ){
        //上传
    }

    fun upload(url: String, params: Map<String, String>,files: Array<File>,loadCallback: LoadCallback ){
        //多文件上传
    }

    fun register():HttpService{
        return RetrofitFactory.instance.create(HttpService::class.java)
    }

    private fun getParamStr(params: Map<String, String>?): String {
        return if (params == null) "" else Gson().toJson(params)
    }

}
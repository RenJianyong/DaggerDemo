package com.rjy.http.retrofit

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.rjy.http.ext.execute
import com.rjy.http.rx.BaseSuscriber
import com.rjy.http.encry.EncryListener
import com.rjy.http.entity.MessageEntity
import com.rjy.http.retrofit.callback.HttpCallback
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.json.JSONObject
import rx.Observable
import rx.functions.Func1

class HttpUtil<T:MessageEntity> constructor(val mContext: Context, val mClassOfT:Class<T>,url:String, params:String, encryListener: EncryListener?) : AnkoLogger {

    val mUrl: String //请求路径
    val mParams: String //请求参数
    val mEncryListener: EncryListener? //加解密接口
    val header: Map<String, String>

    init {
        header = mutableMapOf()
        mUrl = url
        mParams = params
        mEncryListener = encryListener
        header.put("requestHeadMsgId","000"+System.currentTimeMillis());
    }

    fun register(): HttpService {
        return RetrofitFactory.instance.create(HttpService::class.java)
    }

    fun post(httpCallback: HttpCallback<T>) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), mParams)
        register().post(header, mUrl, requestBody)
                .flatMap(object : Func1<String, Observable<T>> {
                    override fun call(t: String): Observable<T> {
                        val fromJson = Gson().fromJson(t.toString(), mClassOfT)
                        return Observable.just(fromJson)
                    }
                })
                .execute(object : BaseSuscriber<T>() {
                    override fun onNext(t: T) {
                        //判断
                        if ("1".equals(t.STATUS)){
                            //成功
                            httpCallback.onSuccess(t)
                        }else{
                            //失败
                            httpCallback.onFail(t.STATUS,t.MSG)
                        }
                    }

                    override fun onCompleted() {
                        //请求结束
                        httpCallback.onCompleted()
                    }

                    override fun onError(e: Throwable?) {
                        //失败
                        httpCallback.onFail("-1","网络请求失败")
                    }
                })
    }


    class Builder<T:MessageEntity> constructor(val context: Context, val classOfT: Class<T>) {
        lateinit var mUrl: String
        lateinit var mParams: String
        var mEncryListener: EncryListener? = null

        /**
         * 设置请求地址
         */
        fun url(url: String): Builder<T> {
            this.mUrl = url
            return this
        }

        /**
         * 设置请求参数
         */
        fun setParams(params: String): Builder<T> {
            this.mParams = if (TextUtils.isEmpty(params)) "" else params
            return this
        }

        /**
         * 设置请求参数
         */
        fun setParams(params: Map<String, String>?): Builder<T> {
            return setParams(getParamStr(params))
        }

        /**
         * 设置加密方式
         */
        fun setEncry(encryListener: EncryListener): Builder<T> {
            this.mEncryListener = encryListener
            return this
        }

        fun noEncry(): Builder<T> {
            this.mEncryListener = null
            return this
        }

        private fun getParamStr(params: Map<String, String>?): String {
            return if (params == null) "" else Gson().toJson(params)
        }

        fun build(): HttpUtil<T> {
            return HttpUtil(context, classOfT, mUrl, mParams, mEncryListener)
        }

    }


}


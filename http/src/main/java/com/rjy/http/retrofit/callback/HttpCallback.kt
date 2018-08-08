package com.rjy.http.retrofit.callback

interface HttpCallback<T> {

    /**
     * 成功回调方法
     */
    fun onSuccess(t:T)

    /**
     * 失败回调方法
     */
    fun onFail(errCode:String, errMsg:String)

    /**
     * 请求结束
     */
    fun onCompleted()

}
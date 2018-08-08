package com.rjy.dagger.base.mvp

/**
 * Mode层返回数据给Presenter层
 */
interface MvpCallback<T> {

    /**
     * 数据返回成功，且数据正确
     * @param data 返回的数据
     */
    fun onSuccess(data:T);

    /**
     * 数据返回成功，但是数据错误
     */
    fun onFailure(code:Int, msg:String);

    /**
     * 获取数据异常
     */
    fun onError(e:Exception)

    /**
     * 数据获取结束
     */
    fun onComplete()

}
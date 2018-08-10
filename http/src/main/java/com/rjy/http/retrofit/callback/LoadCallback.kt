package com.rjy.http.retrofit.callback

import java.io.File

/**
 * 下载/上传进度监听
 */
abstract open class LoadCallback {

    /**
     * 开始下载/上传
     */
    fun onStartDownload(){}
    /**
     * 正在下载/上传
     */
    fun onProgress(current:Long, total:Long){}
    /**
     * 下载/上传完成
     */
    abstract fun onFinish(file:File)
    /**
     * 下载/上传失败
     */
    abstract fun onFail(errCode:String,msg:String)
}
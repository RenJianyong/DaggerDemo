package com.rjy.http.retrofit.body

import com.rjy.http.retrofit.callback.LoadCallback
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

class JsResponseBody constructor(val responseBody: ResponseBody, val downloadCallback:LoadCallback) : ResponseBody() {

    lateinit var mBufferedSource:BufferedSource

    override fun contentType(): MediaType {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (mBufferedSource == null){
            mBufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return mBufferedSource
    }

    private fun source(source: Source):Source{
        return object : ForwardingSource(source){
            var totalBytesRead:Long = 0
            override fun read(sink: Buffer?, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0L
                if (null != downloadCallback){
                    downloadCallback.onProgress(responseBody.contentLength(),totalBytesRead)
                }
                return bytesRead
            }
        }
    }
}
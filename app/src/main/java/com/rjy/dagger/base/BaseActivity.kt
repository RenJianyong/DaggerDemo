package com.rjy.dagger.base

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity

/**
 * Activity基类
 */
open class BaseActivity : AppCompatActivity(){

    lateinit var activity: BaseActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
    }

    val mHandler = object: Handler() {
        override fun handleMessage(msg: Message) {
            handleMessageImpl(msg)
        }
    }

    open fun handleMessageImpl(msg: Message){

    }

}
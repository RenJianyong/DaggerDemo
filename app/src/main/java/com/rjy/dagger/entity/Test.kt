package com.rjy.dagger.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.rjy.dagger.BR

class Test : BaseObservable() {
    @Bindable
    var name: String = ""
    @Bindable
    var testName:String? = null
    @Bindable
    var visible:Boolean = true
    @Bindable
    var pwd:String = ""
}
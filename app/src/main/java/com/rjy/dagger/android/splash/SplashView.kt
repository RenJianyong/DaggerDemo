package com.rjy.dagger.android.splash

import com.rjy.dagger.base.mvp.MvpView

interface SplashView : MvpView  {

    /**
     * 根据code和msg弹出alert
     */
    fun showAlertDialog(code:String,msg:String)

}
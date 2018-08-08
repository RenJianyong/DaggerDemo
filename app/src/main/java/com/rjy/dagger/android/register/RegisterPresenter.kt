package com.rjy.dagger.android.register

import android.view.View
import com.rjy.dagger.base.mvp.MvpBasePresenter

class RegisterPresenter(mvpView: RegisterView) : MvpBasePresenter<RegisterView,RegisterEngine>(mvpView) {
    override fun initEngine(): Class<RegisterEngine> {
        return RegisterEngine::class.java
    }

    fun test(view:View){
        mMvpView.showLoading()
    }

}
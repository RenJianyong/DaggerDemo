package com.rjy.dagger.android.login

import com.rjy.dagger.base.mvp.MvpBasePresenter

class LoginPresenter(mvpView: LoginView) : MvpBasePresenter<LoginView, LoginEngine>(mvpView) {
    override fun initEngine(): Class<LoginEngine> {
        return LoginEngine::class.java
    }


}
package com.rjy.dagger.android.main

import com.rjy.dagger.base.mvp.MvpBasePresenter

class MainMvpPresenter(mvpView:MainMvpView) : MvpBasePresenter<MainMvpView,MainEngine>(mvpView) {
    override fun initEngine(): Class<MainEngine> {
        return MainEngine::class.java
    }


}
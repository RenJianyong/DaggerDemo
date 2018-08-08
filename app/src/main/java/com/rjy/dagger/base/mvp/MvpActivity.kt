package com.rjy.dagger.base.mvp

import android.os.Bundle
import com.rjy.dagger.base.BaseActivity

/**
 * activity实现MVP都需要集成该类
 */
abstract class MvpActivity<T : MvpBasePresenter<*,*>> : BaseActivity(),MvpView {

    /**
     * Presenter对象，每个实现MVP的activity都应该有一个
     */
    lateinit var mPresenter:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    /**
     * 初始化Presenter对象
     */
    abstract fun initPresenter():T


}
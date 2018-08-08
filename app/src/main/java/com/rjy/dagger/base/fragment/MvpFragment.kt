package com.rjy.dagger.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rjy.dagger.base.mvp.MvpBasePresenter
import com.rjy.dagger.base.mvp.MvpView

abstract class MvpFragment<T : MvpBasePresenter<*,*>> : BaseFragment(),MvpView {

    lateinit var mPresenter:T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = initPresenter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    abstract fun initPresenter():T



}
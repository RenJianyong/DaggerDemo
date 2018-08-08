package com.rjy.dagger.base.mvp

interface MvpView {
    /**
     * 显示加载等待层
     */
    fun showLoading()

    /**
     * 隐藏加载等待层
     */
    fun hideLoading()

}
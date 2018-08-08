package com.rjy.dagger.base.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {


    lateinit var mView : View
    var fitsSystemWindowsView:View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(getContentLayout(),container,false)
        mView = view!!
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
        initAction()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (fitsSystemWindowsView != null) {
            if (hidden) {
                fitsSystemWindowsView!!.fitsSystemWindows = false;
            } else {
                fitsSystemWindowsView!!.fitsSystemWindows = true;
            }
            fitsSystemWindowsView!!.requestApplyInsets();
        }
        super.onHiddenChanged(hidden);
    }

    /**
     * 布局文件
     */
    abstract fun getContentLayout():Int

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化事件监听
     */
    abstract fun initAction()

}
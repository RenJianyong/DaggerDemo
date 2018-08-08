package com.rjy.dagger.android.main.fragment.home

import android.app.ProgressDialog
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rjy.dagger.R
import com.rjy.dagger.android.main.adapter.MenuAdapter
import com.rjy.dagger.android.main.adapter.SpaceItemDecoration
import com.rjy.dagger.android.main.entity.MenuEntity
import com.rjy.dagger.base.fragment.MvpFragment
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : MvpFragment<HomeMvpPresenter>(), HomeMvpView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val createView = super.onCreateView(inflater, container, savedInstanceState)
        fitsSystemWindowsView = mView.mTopBar
        fitsSystemWindowsView!!.fitsSystemWindows = true;
        return createView
    }

    override fun initMenuData(array: Array<MenuEntity>) {
        mAdapter.setDatas(array)
    }

    lateinit var mAdapter: MenuAdapter
    override fun initPresenter(): HomeMvpPresenter {
        val mvpPresenter = HomeMvpPresenter(this)
        return mvpPresenter
    }

    override fun getContentLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mView.mPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        mView.mPrice1Tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

    override fun initData() {
        mAdapter = MenuAdapter(null)
        mView.mMenuRecyclerView.layoutManager = GridLayoutManager(this!!.activity,5)
        mView.mMenuRecyclerView.addItemDecoration(SpaceItemDecoration(activity,5))
        mView.mMenuRecyclerView.adapter = mAdapter

        mPresenter.initMenuData()
    }

    override fun initAction() {
    }
}
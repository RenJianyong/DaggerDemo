package com.rjy.dagger.android.main.fragment.cart



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rjy.dagger.R
import com.rjy.dagger.base.fragment.BaseFragment
import com.rjy.http.retrofit.HttpUtil
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val createView = super.onCreateView(inflater, container, savedInstanceState)
        fitsSystemWindowsView = mView.mTopBar
        fitsSystemWindowsView!!.fitsSystemWindows = true
        return createView
    }
    override fun getContentLayout(): Int {
        return R.layout.fragment_cart
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initAction() {
        mView.mTestBtn.setOnClickListener {
        }
    }


}

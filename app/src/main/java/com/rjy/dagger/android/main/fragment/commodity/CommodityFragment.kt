package com.rjy.dagger.android.main.fragment.commodity

import android.content.res.Resources
import com.rjy.dagger.R
import com.rjy.dagger.base.fragment.BaseFragment
import com.rjy.dagger.widget.LoadingDialog
import kotlinx.android.synthetic.main.fragment_commodity.view.*

class CommodityFragment : BaseFragment() {

    var dialog:LoadingDialog? = null

    override fun getContentLayout(): Int {
        return R.layout.fragment_commodity
    }

    override fun initView() {

        if (dialog == null){
            dialog = LoadingDialog(activity,0)
        }

    }

    override fun initData() {
    }

    override fun initAction() {
        mView.mCommodityTv.setOnClickListener {
            if (!dialog!!.isShowing){
                dialog!!.show()
            }
        }
    }
}
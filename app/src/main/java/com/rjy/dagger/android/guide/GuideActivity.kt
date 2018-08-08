package com.rjy.dagger.android.guide

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.rjy.dagger.R
import com.rjy.dagger.base.BaseActivity
import com.rjy.dagger.android.main.MainActivity
import kotlinx.android.synthetic.main.activity_guide.*
import org.jetbrains.anko.startActivity

class GuideActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    lateinit var mAdapter:GuideAdapter
    val datas:IntArray = intArrayOf(R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        initData()
        initAction()
    }

    private fun initAction() {
        mNextTv.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }

    private fun initData() {
        mAdapter = GuideAdapter(datas)
        mViewPage.adapter = this.mAdapter
        mViewPage.addOnPageChangeListener(this)

    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position == 2){
            mNextTv.visibility = View.VISIBLE
        }else{
            mNextTv.visibility = View.GONE
        }
    }

}

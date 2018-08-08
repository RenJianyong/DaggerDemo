package com.rjy.dagger.android.guide

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.rjy.dagger.R
import kotlinx.android.synthetic.main.item_guide_page.view.*

class GuideAdapter constructor(private var datas:IntArray?) : PagerAdapter() {
    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view==`object`
    }

    override fun getCount(): Int {
        return if (datas == null) 0 else datas!!.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var view = View.inflate(container!!.context, R.layout.item_guide_page, null)
        view.mImageView.setImageResource(datas!![position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container!!.removeView(`object` as View?)
    }

}
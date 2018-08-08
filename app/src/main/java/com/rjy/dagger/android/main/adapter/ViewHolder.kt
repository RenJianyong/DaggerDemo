package com.rjy.dagger.android.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_main_menu.view.*

class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

    lateinit var mMenuImgIv:ImageView
    lateinit var mMenuNameTv:TextView

    init {
        mMenuNameTv = view.mMenuNameTv
        mMenuImgIv = view.mMenuImgIv

        view.setOnClickListener {

            //

        }
    }

}
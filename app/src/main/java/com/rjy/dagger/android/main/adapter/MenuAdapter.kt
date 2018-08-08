package com.rjy.dagger.android.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.rjy.dagger.R
import com.rjy.dagger.android.main.entity.MenuEntity
import com.squareup.picasso.Picasso

class MenuAdapter constructor(private var datas:Array<MenuEntity>?) : RecyclerView.Adapter<ViewHolder>() {

    fun setDatas(datas:Array<MenuEntity>?){
        this.datas = datas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = View.inflate(parent!!.context, R.layout.item_main_menu, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (datas == null) 0 else datas!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = datas!![position]
        val url:String = "file:///android_asset/"+entity.menuImg
        holder.mMenuNameTv.text = entity.menuName

        Picasso.with(holder.itemView.context)
                .load(url)
                .into(holder.mMenuImgIv)

//        Glide.with(holder.itemView)
//                .load(url)
//                .into(holder.mMenuImgIv)

    }

}
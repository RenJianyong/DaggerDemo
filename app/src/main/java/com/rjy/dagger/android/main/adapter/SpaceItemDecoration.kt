package com.rjy.dagger.android.main.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

class SpaceItemDecoration constructor(val context: Context,val space:Int) : RecyclerView.ItemDecoration() {

    lateinit var mDivider:Drawable

    companion object {
        val ATTRS:IntArray = IntArray(1,{android.R.attr.listDivider})
    }

    init {
        val typedArray = context.obtainStyledAttributes(ATTRS)
        mDivider = typedArray.getDrawable(0)
        typedArray.recycle()
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
//        drawHorizontal(c!!, parent!!)
//        drawVertical(c!!,parent!!)
    }

    fun getSpanCount(parent: RecyclerView):Int{
        var spanCount:Int = -1
        var layoutManager:RecyclerView.LayoutManager = parent.layoutManager;
        if (layoutManager is GridLayoutManager){
            spanCount = layoutManager.spanCount
        }else if (layoutManager is StaggeredGridLayoutManager){
            spanCount = layoutManager.spanCount
        }

        return spanCount

    }

    fun drawHorizontal(c:Canvas,parent: RecyclerView) {
        var childCount: Int = parent.childCount
        var i:Int = 0
        while (i<childCount){
            val childAt = parent.getChildAt(i)
            val layoutParams:RecyclerView.LayoutParams = childAt.layoutParams as RecyclerView.LayoutParams
            val left:Int = childAt.left-layoutParams.leftMargin
            val right:Int = childAt.right+layoutParams.rightMargin+mDivider.intrinsicWidth
            val top = childAt.bottom+layoutParams.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
            i++
        }
    }

    fun drawVertical(c:Canvas,parent: RecyclerView) {
        var childCount: Int = parent.childCount
        var i:Int = 0
        while (i<childCount){
            val childAt = parent.getChildAt(i)
            val layoutParams:RecyclerView.LayoutParams = childAt.layoutParams as RecyclerView.LayoutParams
            val left:Int = childAt.right+layoutParams.rightMargin
            val right:Int = left+mDivider.intrinsicWidth
            val top = childAt.top-layoutParams.topMargin
            val bottom = childAt.bottom + layoutParams.bottomMargin
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
            i++
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {

        outRect!!.left = space
        outRect!!.top = space
        outRect!!.right = space
        outRect!!.bottom = space

    }

}
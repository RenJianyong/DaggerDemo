package com.rjy.dagger.widget

import android.content.Context
import android.graphics.PointF
import android.media.audiofx.Visualizer
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import com.rjy.dagger.R
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.forEachChild

class PullRefreshLayout(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr), View.OnTouchListener {

    val mDownPoint:PointF = PointF()
    lateinit var mHeadView:View

    constructor(context: Context?): this(context,null){

    }

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0){

    }

    init{
        orientation = VERTICAL
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        mHeadView = getChildAt(0)
        val view:View = getChildAt(1)
        if (view is ScrollView){
            view.setOnTouchListener(this)
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {

        var b:Boolean = false



        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                mDownPoint.set(event.rawX,event.rawY)
            }
            MotionEvent.ACTION_MOVE -> {
                if (view is ScrollView) {
                    if (view.scrollY == 0) {
                        //滑动到顶部
                        val l = event.rawY - mDownPoint.y
                        if (mHeadView is HeadView) {
                            if (l > (mHeadView as HeadView).mMaxHeight) {
                                mDownPoint.y += l-(mHeadView as HeadView).mMaxHeight
                                (mHeadView as HeadView).setProgress(1f)
                                b = true
                            } else if (l > 0) {
                                (mHeadView as HeadView).setProgress(l / (mHeadView as HeadView).mMaxHeight)
                                b = true
                            }else{
                                mDownPoint.set(event.rawX,event.rawY)
                            }
                        }

                    }else{
                        mDownPoint.set(event.rawX,event.rawY)
                        (mHeadView as HeadView).setProgress(0f)
                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                if (mHeadView is HeadView) {
                    (mHeadView as HeadView).release()
                }
            }

        }


        Log.d("progress","x:${event.x}==y:${event.y}")

        return if (b) b else super.onTouchEvent(event)
    }

}
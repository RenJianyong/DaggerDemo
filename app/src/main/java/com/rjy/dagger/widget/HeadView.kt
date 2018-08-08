package com.rjy.dagger.widget

import android.content.Context
import android.graphics.*
import android.text.style.UpdateLayout
import android.util.AttributeSet
import android.view.View
import com.rjy.dagger.utils.GeometryUtil
import android.R.string.cancel
import android.animation.ValueAnimator
import android.util.Log
import android.view.animation.DecelerateInterpolator



class HeadView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): View(context, attrs, defStyleAttr) {


    constructor(context: Context?): this(context,null){

    }

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0){

    }

    //固定圆的圆心
    val mCenter: PointF = PointF()
    //固定圆的当前半径
    var mRodius: Float = 40f
    //下拉的最大高度
    val mMaxHeight:Float = 300f
    //下拉进度
    var mProgress:Float = 0f
    val mPaint:Paint
    var mColor:Int = Color.RED

    val mPath:Path = Path()

    var valueAnimator:ValueAnimator? = null
    var mProgressInterpolator: DecelerateInterpolator = DecelerateInterpolator()
    init {
        //设定抗锯齿画笔
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        //防抖动
        mPaint.isDither = true
        //设置填充方式
        mPaint.style = Paint.Style.FILL
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND
        //画笔颜色
        mPaint.color = mColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //计算控件大小
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //计算动圆和定圆所占矩形的宽
        var minWidth:Float = mRodius*4+paddingLeft + paddingRight
        //计算动圆和定圆所占矩形的高
        var minHeight:Float = mMaxHeight*mProgress+mRodius*mProgress

        val measureWidth: Int
        val measureHeight: Int = minHeight.toInt()
        //三种模式是EXACTLY,UNSPECIFIED,AT_MOST,分别代表精确大小,不精确大小,最大值
        if (widthMode === View.MeasureSpec.EXACTLY) {//精确值
            measureWidth = widthSize
        } else if (widthMode === View.MeasureSpec.AT_MOST) {//最大值
            measureWidth = Math.min(minWidth.toInt(), widthSize)
        } else {
            measureWidth = minWidth.toInt()
        }

        setMeasuredDimension(measureWidth,measureHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCenter.set(width/2f,mMaxHeight*mProgress-mRodius)
        updatePath()
    }

    private fun updatePath() {
        mPath.reset()
        val pointO:PointF = PointF()
        pointO.set(mCenter)
        val pointA:PointF = PointF(width/4*mProgress,0f)
        val pointC:PointF = PointF(width/2f-(1-mProgress)*width/4f,0f)
        val pointF:PointF = PointF(width/2f+(1-mProgress)*width/4f,0f)
        val angle1:Double = Math.atan((pointO.y/(width/2-pointC.x)).toDouble())
        val angle2:Double = Math.asin((mRodius/GeometryUtil.getDistanceBetween2Points(pointC,pointO)).toDouble())
        val angle:Double = angle1+angle2
        val pointB:PointF = PointF((pointO.x-Math.sin(angle)*mRodius).toFloat(), (pointO.y+Math.cos(angle)*mRodius).toFloat())
        val pointD:PointF = PointF((pointO.x+Math.sin(angle)*mRodius).toFloat(), (pointO.y+Math.cos(angle)*mRodius).toFloat())
        val pointE:PointF = PointF(width.toFloat()-width/4*mProgress,0f)

        mPath.moveTo(pointA.x,pointA.y)
        mPath.quadTo(pointC.x,pointC.y,pointB.x,pointB.y)
        mPath.lineTo(pointD.x,pointD.y)
        mPath.quadTo(pointF.x,pointF.y,pointE.x,pointE.y)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画贝塞尔曲线
        canvas.drawPath(mPath,mPaint)
        canvas.drawCircle(mCenter.x,mCenter.y,mRodius,mPaint)
    }

    fun setProgress(progress:Float){
        Log.d("progress","${progress}")
        this.mProgress = progress
        requestLayout()
    }

    fun release() {
        if (valueAnimator == null) {
            val animator = ValueAnimator.ofFloat(mProgress, 0f)
            animator.interpolator = DecelerateInterpolator()
            animator.duration = 300
            animator.addUpdateListener {
                val anim = animator.animatedValue
                if (anim is Float) {
                    setProgress(anim)
                }
            }
            valueAnimator = animator
        } else {
            valueAnimator!!.cancel()
            valueAnimator!!.setFloatValues(mProgress, 0f)
        }
        valueAnimator!!.start()
    }
}
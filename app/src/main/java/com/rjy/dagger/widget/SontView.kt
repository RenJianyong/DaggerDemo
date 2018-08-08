package com.rjy.dagger.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import com.rjy.dagger.R
import com.rjy.dagger.utils.GeometryUtil



class SontView (context: Context?, attrs: AttributeSet?, defStyleAttr: Int): View(context, attrs, defStyleAttr) {


    constructor(context: Context?): this(context,null){

    }

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0){

    }

    //固定圆的圆心
    val mStickCenter:PointF = PointF()
    //固定圆的初始半径
    var mStickInitialRodius:Float = 30f
    //固定圆的当前半径
    var mStickRodius: Float = 30f
    //动圆的圆心,移动的点
    val mDragCenter:PointF = PointF()
    //动圆的半径
    var mDragRodius:Float = 35f
    //动圆拖动的范围
    private var mRange:Float = 180f
    //是否允许拖动
    var isAllowDrag:Boolean = false
    //拖动是否断裂
    var isCrack:Boolean = false
    //控件是否已经消失
    var isDisappear:Boolean = false
    //消息数量
    private var mMessageNum:Int = 0
    //圆的画笔
    val mCirclePaint : Paint
    val mPath : Path
    private var mColor:Int = Color.BLACK
    //文字画笔
    val mTextPaint:Paint
    private var mTextColor:Int = Color.WHITE
    private var mTextSize:Float = 35f
    //设置边框
    private var mPadding:Float = 0f

    private var onMessageStateListener: (() -> Unit)? = null

    init {

        //初始化
        if (attrs != null && context != null) {
            val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SontView)
            //设置小鼻涕的颜色
            mColor = typedArray.getColor(R.styleable.SontView_color, Color.BLACK)
            //设置拖动范围
            mRange = typedArray.getDimension(R.styleable.SontView_range, 180f)
            //文字颜色
            mTextColor = typedArray.getColor(R.styleable.SontView_textColor, Color.WHITE)
            //文字大小
            mTextSize = typedArray.getDimension(R.styleable.SontView_textSize, 35f)
            //消息数量
            mMessageNum = typedArray.getInt(R.styleable.SontView_messageNum, 0)
            //内边框
            mPadding = typedArray.getDimension(R.styleable.SontView_padding, 0f)

            typedArray.recycle()
        }

        //设定抗锯齿画笔
        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        //防抖动
        mCirclePaint.isDither = true
        //设置填充方式
        mCirclePaint.style = Paint.Style.FILL
        mCirclePaint.strokeCap = Paint.Cap.ROUND
        mCirclePaint.strokeJoin = Paint.Join.ROUND
        //画笔颜色
        mCirclePaint.color = mColor

        mPath = Path()
        //初始化文字画笔
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        //防抖动
        mTextPaint.isDither = true
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize
        //计算圆的大小
        val rect: Rect = Rect()
        mTextPaint.getTextBounds("99", 0, 2, rect)
        mDragRodius = rect.width().toFloat() + mPadding*2
        mStickInitialRodius = mDragRodius * 0.86f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //计算控件大小
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //计算动圆和定圆所占矩形的宽
        var minWidth:Float = mDragRodius*2+paddingLeft + paddingRight
        //计算动圆和定圆所占矩形的高
        var minHeight:Float = mDragRodius*2 + paddingTop + paddingBottom

        val measureWidth: Int
        val measureHeight: Int
        //三种模式是EXACTLY,UNSPECIFIED,AT_MOST,分别代表精确大小,不精确大小,最大值
        if (widthMode === View.MeasureSpec.EXACTLY) {//精确值
            measureWidth = widthSize
        } else if (widthMode === View.MeasureSpec.AT_MOST) {//最大值
            measureWidth = Math.min(minWidth.toInt(), widthSize)
        } else {
            measureWidth = minWidth.toInt()
        }

        if (heightMode === View.MeasureSpec.EXACTLY) {//精确值
            measureHeight = heightSize
        } else if (heightMode === View.MeasureSpec.AT_MOST) {//最大值
            measureHeight = Math.min(minHeight.toInt(), heightSize)
        } else {
            measureHeight = minHeight.toInt()
        }
        //设置测量的宽和高
        setMeasuredDimension(measureWidth, measureHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //设置定圆在中心位置
        mStickCenter.set(w/2f, h/2f)
        mDragCenter.set(w/2f, h/2f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isDisappear&&mMessageNum>0) {
            updatePath()
            if (!isCrack) {
                canvas.drawCircle(mStickCenter.x, mStickCenter.y, mStickRodius, mCirclePaint)
                //画贝塞尔曲线
                canvas.drawPath(mPath, mCirclePaint);
            }
            canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRodius, mCirclePaint)
            val fontMetrics = mTextPaint.fontMetrics
            val center = (fontMetrics.bottom + fontMetrics.top) / 2
            val rect:Rect = Rect()
            mTextPaint.getTextBounds("${mMessageNum}",0,"${mMessageNum}".length,rect)
            val x:Float = mDragCenter.x-rect.width()/2
            val y:Float = mDragCenter.y - center
            canvas.drawText("${mMessageNum}",x, y,mTextPaint)
        }
    }

    /**
     * 路径更新
     */
    fun updatePath(){
        //计算两圆心间的距离
        val d = GeometryUtil.getDistanceBetween2Points(mStickCenter,mDragCenter)
        val offset = d/mRange*0.5f
        mStickRodius = mStickInitialRodius*(1-offset)
        //定圆的坐标点
        val x1:Float = mStickCenter.x
        val y1:Float = mStickCenter.y
        //动圆的坐标的
        val x2:Float = mDragCenter.x
        val y2:Float = mDragCenter.y

        val cosAngle:Float = (y2-y1)/d
        val sinAngle:Float = (x2-x1)/d

        //计算开始点和结束点
        val pointA:PointF = PointF(x1-mStickRodius*cosAngle,y1+mStickRodius*sinAngle)
        val pointB:PointF = PointF(x1+mStickRodius*cosAngle,y1-mStickRodius*sinAngle)
        val pointC:PointF = PointF(x2+mDragRodius*0.8f*cosAngle,y2-mDragRodius*0.8f*sinAngle)
        val pointD:PointF = PointF(x2-mDragRodius*0.8f*cosAngle,y2+mDragRodius*0.8f*sinAngle)
        //计算两个控制点
        val middlePoint:PointF = GeometryUtil.getMiddlePoint(mStickCenter, mDragCenter)
        val xO:Float = middlePoint.x-mStickRodius/2*cosAngle
        val yO:Float = middlePoint.y+mStickRodius/2*sinAngle
        val xP:Float = middlePoint.x+mStickRodius/2*cosAngle
        val yP:Float = middlePoint.y-mStickRodius/2*sinAngle
        //复位
        mPath.reset()
        mPath.moveTo(pointA.x,pointA.y)
        mPath.quadTo(xO,yO,pointD.x,pointD.y)
        mPath.lineTo(pointC.x,pointC.y)
        mPath.quadTo(xP,yP,pointB.x,pointB.y)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //记录按下点，作为移动圆圆心起始点
                //按下点在定圆的范围，则可以拖动
                val between2Points = GeometryUtil.getDistanceBetween2Points(mDragCenter, PointF(event.x, event.y))
                if (between2Points <= mDragRodius){
                    //设置可拖动
                    isAllowDrag = true

                }
            }
            MotionEvent.ACTION_MOVE -> {
                //开始拖动，重新计算控件大小
                //控件是可拖动状态
                if (isAllowDrag){
                    //设置动圆中心为移动点
                    mDragCenter.set(event.x,event.y)
                    //判断是否断裂
                    val between2Points = GeometryUtil.getDistanceBetween2Points(mDragCenter, mStickCenter)
                    if(between2Points>mRange){
                        //超出范围设置断裂
                        isCrack = true
                    }
                    //重新绘制控件
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                //控件是可拖动状态
                if (isAllowDrag) {
                    //恢复控件大小
                    //是否已经断裂
                    val between2Points = GeometryUtil.getDistanceBetween2Points(mDragCenter, mStickCenter)
                    if (between2Points > mRange) {
                        //超出范围设置消失
                        isDisappear = true
                        invalidate()
                        onMessageStateListener?.invoke()

                    } else {
                        //未超出，设置复原
                        //启动恢复动画
                        setAnimator(PointF(event.x, event.y))
                    }
                }

            }
        }
        return true
    }

    private fun setAnimator(upPoint:PointF) {
        val va = ValueAnimator.ofFloat(1.0f)
        va.addUpdateListener { animation ->
            val animatedFraction = animation.animatedFraction
            //获取手指离开点的距离
            val pointByPercent = GeometryUtil.getPointByPercent(upPoint, mStickCenter, animatedFraction)
            //动态设定距离
            mDragCenter.set(pointByPercent)
            invalidate()
        }
        va.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //动画执行完成
                //恢复控件状态
                mDragCenter.set(mStickCenter)
                invalidate()
                isAllowDrag = false
                isCrack = false
            }
        })
        //重力回弹
        if(!isCrack) {
            va.interpolator = OvershootInterpolator(4f)
        }
        //时长
        va.duration = 200
        va.start()
    }

    /**
     * 设置消息数量
     */
    fun setMessageNum(messageNum:Int){
        if (messageNum>=100){
            this.mMessageNum = 99
        }else {
            this.mMessageNum = messageNum
        }
        //重置控件，重新绘制
        resetView()
    }
    fun getMessageNum():Int{
        return mMessageNum
    }

    /**
     * 设置小球画笔颜色
     */
    fun setColor(color:Int){
        mColor = color
        mCirclePaint.color = color
        resetView()
    }

    fun getColor():Int{
        return mColor
    }

    /**
     * 设置拖动范围
     */
    fun setRange(range:Float){
        mRange = range
    }
    fun getRange():Float{
        return mRange
    }

    /**
     * 设置文字颜色
     */
    fun setTextColor(textColor:Int){
        mTextColor = textColor
        mTextPaint.color = textColor
        resetView()
    }
    fun getTextColor():Int{
        return mTextColor
    }

    /**
     * 设置文字大小
     */
    fun setTextSize(textSize:Float){
        mTextSize = textSize
        mTextPaint.textSize = textSize

        //计算圆的大小
        val rect: Rect = Rect()
        mTextPaint.getTextBounds("99", 0, 2, rect)
        mDragRodius = rect.width().toFloat() + mPadding*2
        mStickInitialRodius = mDragRodius * 0.86f

        requestLayout()
    }
    fun getTextSize():Float{
        return mTextSize
    }

    fun setPadding(padding: Float){
        mPadding = padding
        //计算圆的大小
        val rect: Rect = Rect()
        mTextPaint.getTextBounds("99", 0, 2, rect)
        mDragRodius = rect.width().toFloat() + mPadding*2
        mStickInitialRodius = mDragRodius * 0.86f

        requestLayout()
    }
    fun getPadding():Float{
        return mPadding
    }
    /**
     * 重置View
     */
    private fun resetView() {
        //是否允许拖动
        isAllowDrag = false
        //拖动是否断裂
        isCrack = false
        //控件是否已经消失
        isDisappear = false
        //重置动圆的位置
        mDragCenter.set(mStickCenter)
        //重置定圆的半径
        mStickRodius = mStickInitialRodius
        mPath.reset()
        invalidate()
    }

    fun setOnMessageStateListener(onMessageStateListener:(()->Unit)?){
        this.onMessageStateListener = onMessageStateListener
    }

}
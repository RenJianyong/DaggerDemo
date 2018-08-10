package com.rjy.dagger

import android.animation.*
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.UserDictionary.Words.APP_ID
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.BounceInterpolator
import com.rjy.dagger.databinding.ActivityTestBinding
import com.rjy.dagger.entity.Test
import com.rjy.dagger.entity.splash.VersionCheckResult
import com.rjy.http.retrofit.HttpUtil
import org.jetbrains.anko.toast



class TestActivity : AppCompatActivity() {

    var mFloat:Boolean = true
    lateinit var mViewDataBinding:ActivityTestBinding
    var isAnimating:Boolean = false
    var isFold:Boolean = true
    var mFoldedViewMeasureHeight:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView<ActivityTestBinding>(this, R.layout.activity_test)
        mViewDataBinding.test = Test()


        mViewDataBinding.mTestIv3.setOnClickListener {
            if (mFloat){
                startAnim()
            }else{
                closeAnim()
            }

        }
        mViewDataBinding.mTestIv2.setOnClickListener {
            if (isFold) {
                if (!isAnimating) {
                    mFoldedViewMeasureHeight = if(mFoldedViewMeasureHeight == 0) mViewDataBinding.mTestName.height else mFoldedViewMeasureHeight
                    scaleAnim(mViewDataBinding.mTestName, mViewDataBinding.mTestName.height, 0)
                }
            }else{
                if (!isAnimating) {
                    mViewDataBinding.test!!.visible = true
                    mViewDataBinding.test!!.notifyPropertyChanged(BR.visible)
                    scaleAnim(mViewDataBinding.mTestName, 0, mFoldedViewMeasureHeight)
                }
            }
            isFold = !isFold
        }

        mViewDataBinding.mTestIv1.setOnClickListener {
            toast("${mViewDataBinding.test!!.pwd}")
        }

        mViewDataBinding.mTestIv4.setOnClickListener {

            val params:MutableMap<String,String> = mutableMapOf()

            params.put("APP_ID","1140")
            params.put("APP_PLAT_TEMP", "04");
            params.put("APP_VERS", "1.0.0");

            val httpUtil = HttpUtil.Builder<VersionCheckResult>(this, VersionCheckResult::class.java)
                    .url("json/app/appManage/getApkUpdateInfo.do")
                    .setParams(params)
                    .noEncry()
                    .build()
//            httpUtil.post()
        }
    }

    fun scaleAnim(view:View,startValue:Int,endValue:Int){
        isAnimating = true
        val animator:ValueAnimator = ValueAnimator.ofInt(startValue,endValue)
        animator.duration = 500
        animator.addUpdateListener {
            val animatedValue = it.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                if(endValue == 0) {
                    mViewDataBinding.test!!.visible = false
                    mViewDataBinding.test!!.notifyPropertyChanged(BR.visible)
                }
                isAnimating = false
            }
        })
        animator.start()
    }

    fun startAnim(){
        mViewDataBinding.test!!.name = "启动动画"
        mViewDataBinding.test!!.notifyPropertyChanged(BR.name)
        val animator1 = ObjectAnimator.ofFloat(mViewDataBinding.mTestIv1, "translationX", -200F)
        val valuesHolder = PropertyValuesHolder.ofFloat("translationX", -(200/Math.sqrt(2.0)).toFloat())
        val valuesHolder1 = PropertyValuesHolder.ofFloat("translationY", -(200/Math.sqrt(2.0)).toFloat())
        val animator2 = ObjectAnimator.ofPropertyValuesHolder(mViewDataBinding.mTestIv2,valuesHolder,valuesHolder1)
        val animator3 = ObjectAnimator.ofFloat(mViewDataBinding.mTestIv4, "translationY", -200F)
//        val animator4 = ObjectAnimator.ofFloat(mTestIv5, "translationY", 200F)
        val animatorSet:AnimatorSet = AnimatorSet()
        animatorSet.setDuration(500)
        animatorSet.interpolator = BounceInterpolator()
        animatorSet.playTogether(
                animator1,
                animator2,
                animator3
        )
        animatorSet.start()
        mFloat = false
    }

    fun closeAnim(){
        mViewDataBinding.test!!.name = "关闭动画"
        mViewDataBinding.test!!.notifyPropertyChanged(BR.name)
        val animator1 = ObjectAnimator.ofFloat(mViewDataBinding.mTestIv1, "translationX", 0F)
        val animator2 = ObjectAnimator.ofFloat(mViewDataBinding.mTestIv2, "translationX", 0F)
        val animator3 = ObjectAnimator.ofFloat(mViewDataBinding.mTestIv2, "translationY", 0F)
        val animator4 = ObjectAnimator.ofFloat(mViewDataBinding.mTestIv4, "translationY", 0F)
        val animatorSet:AnimatorSet = AnimatorSet()
        animatorSet.setDuration(300)
        animatorSet.playTogether(
                animator1,
                animator2,
                animator3,
                animator4
        )
        animatorSet.start()
        mFloat = true
    }

}
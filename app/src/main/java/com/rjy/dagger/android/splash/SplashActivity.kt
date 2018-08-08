package com.rjy.dagger.android.splash

import android.os.Bundle
import android.os.Message
import com.rjy.dagger.R
import com.rjy.dagger.android.guide.GuideActivity
import com.rjy.dagger.constants.FIRST_LOADING
import com.rjy.dagger.base.BaseActivity
import com.rjy.dagger.android.main.MainActivity
import com.rjy.dagger.base.mvp.MvpActivity
import com.rjy.dagger.utils.SharedPreferenceUtil
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity

class SplashActivity : MvpActivity<SplashPresenter>(), SplashView {
    override fun showAlertDialog(code: String, msg: String) {
        alert(msg," ",{
            if ("-1" == code){
                this.positiveButton("确定",{
                    finish()
                    System.exit(0)
                })
            }else if("0" == code) {
                mHandler.sendEmptyMessageDelayed(1, 1500)
            }else if ("1" == code){
                this.positiveButton("更新",{
                    mHandler.sendEmptyMessageDelayed(1, 1500)
                })
                this.negativeButton("取消",{
                    mHandler.sendEmptyMessageDelayed(1, 1500)
                })
            }else if ("2" == code){
                this.positiveButton("更新",{
                    mHandler.sendEmptyMessageDelayed(1, 1500)
                })
            }
//            this.negativeButton("取消",{})

        }).show()
    }

    override fun initPresenter(): SplashPresenter {
        return SplashPresenter(this)
    }

    var sharedPreferenceUtil : SharedPreferenceUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this)
//        mHandler.sendEmptyMessageDelayed(1,2500)
        mPresenter.versionCheck(this)
    }

    override fun handleMessageImpl(msg: Message){
        if (msg.what == 1){
            if (sharedPreferenceUtil!!.getBoolean(FIRST_LOADING,true)) {
                startActivity<GuideActivity>()
                sharedPreferenceUtil!!.setBoolean(FIRST_LOADING,false)
            }else{
                startActivity<MainActivity>()
            }
            finish()
        }
    }

    override fun onBackPressed() {
        mHandler.removeMessages(1)
        super.onBackPressed()
    }
}

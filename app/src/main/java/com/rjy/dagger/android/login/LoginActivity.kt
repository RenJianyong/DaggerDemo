package com.rjy.dagger.android.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import com.google.gson.ExclusionStrategy
import com.google.gson.Gson
import com.rjy.dagger.R
import com.rjy.dagger.base.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_login.*
import okio.Okio
import org.jetbrains.anko.toast

class LoginActivity : MvpActivity<LoginPresenter>(),LoginView {
    override fun initPresenter(): LoginPresenter {
        val mvpPresenter = LoginPresenter(this)
        return mvpPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        mRegisterTv.setOnClickListener {
            //点击注册按钮
            toast("注册")
        }

        mForgetPwdTv.setOnClickListener {
            //点击忘记密码按钮
            toast("忘记密码")
        }
    }
}

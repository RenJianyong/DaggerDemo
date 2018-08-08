package com.rjy.dagger.android.register

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rjy.dagger.R
import com.rjy.dagger.base.mvp.MvpActivity
import com.rjy.dagger.databinding.ActivityRegisterBinding
import com.rjy.dagger.entity.User
import org.jetbrains.anko.toast

class RegisterActivity : MvpActivity<RegisterPresenter>(), RegisterView {

    lateinit var mDataBind:ActivityRegisterBinding

    override fun initPresenter(): RegisterPresenter {
        return RegisterPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind = DataBindingUtil.setContentView<ActivityRegisterBinding>(this, R.layout.activity_register)
        mDataBind.user = User()
        mDataBind.presenter = mPresenter
    }

    override fun showLoading() {
        toast("测试")
    }
}

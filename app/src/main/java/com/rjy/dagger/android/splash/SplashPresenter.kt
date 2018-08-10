package com.rjy.dagger.android.splash

import android.content.Context
import com.rjy.dagger.base.mvp.MvpBasePresenter
import com.rjy.dagger.entity.splash.VersionCheckResult
import com.rjy.http.retrofit.HttpClientUtil
import com.rjy.http.retrofit.HttpUtil
import com.rjy.http.retrofit.callback.HttpCallback

class SplashPresenter(mvpView: SplashView) : MvpBasePresenter<SplashView, SplashEngine>(mvpView) {
    override fun initEngine(): Class<SplashEngine> {
        return SplashEngine::class.java
    }

    fun versionCheck(context: Context) {

        mMvpView.showLoading()

        val params: MutableMap<String, String> = mutableMapOf()

        params.put("APP_ID", "1140")
        params.put("APP_PLAT_TEMP", "04");
        params.put("APP_VERS", "1.0.0");

        HttpClientUtil.post<VersionCheckResult>(
                "json/app/appManage/getApkUpdateInfo.do",
                params,
                object : HttpCallback<VersionCheckResult>{
            override fun onSuccess(t: VersionCheckResult) {
            }

            override fun onFail(errCode: String, errMsg: String) {
            }

            override fun onCompleted() {
            }

        })

//        HttpUtil.Builder<VersionCheckResult>(context, VersionCheckResult::class.java)
//                .url("json/app/appManage/getApkUpdateInfo.do")
//                .setParams(params)
//                .noEncry()
//                .build()
//                .post(object : HttpCallback<VersionCheckResult> {
//
//                    override fun onSuccess(t: VersionCheckResult) {
//                        //请求成功
//                        mMvpView.showAlertDialog(t.RES_FORCE_UPDATE,"检测到版本更新")
//                    }
//
//                    override fun onFail(errCode: String, errMsg: String) {
//                        //请求失败
//                        mMvpView.showAlertDialog("-1","版本检查失败，即将退出")
//                    }
//
//                    override fun onCompleted() {
//                        mMvpView.hideLoading()
//                    }
//
//                })
    }
}
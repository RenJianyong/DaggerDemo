package com.rjy.dagger.android.main.fragment.home

import com.google.gson.Gson
import com.rjy.http.ext.execute
import com.rjy.http.rx.BaseSuscriber
import com.rjy.dagger.android.main.entity.MenuEntity
import com.rjy.dagger.application.MyApplication
import com.rjy.dagger.base.mvp.MvpBasePresenter
import com.rjy.dagger.constants.STATIC_MODE
import rx.Observable
import rx.Subscriber

class HomeMvpPresenter(mvpView: HomeMvpView) : MvpBasePresenter<HomeMvpView,HomeEngine>(mvpView) {
    override fun initEngine(): Class<HomeEngine> {
        return HomeEngine::class.java
    }

    /**
     * 初始化菜单数据
     */
    fun initMenuData(){
        //显示等待层
        mMvpView.showLoading()
        //发送接口
        if (STATIC_MODE){
            //静态版本，获取本地数据
            Observable.create(object : Observable.OnSubscribe<Array<MenuEntity>> {
                override fun call(t: Subscriber<in Array<MenuEntity>>?) {
                    val inputStream = MyApplication.instance.assets.open("data/json/menuData")
                    var b : ByteArray = ByteArray(inputStream.available())
                    inputStream.read(b)
                    var json:String = String(b)
                    inputStream.close()
                    var entitys: Array<MenuEntity>? = null
                    try {
                        entitys = Gson().fromJson(json, Array<MenuEntity>::class.java)
                        t!!.onNext(entitys)
                    } catch (e: Exception) {
                        t!!.onError(e)
                    } finally {
                        t!!.onCompleted()
                    }

                }

            }).execute(object : BaseSuscriber<Array<MenuEntity>>() {
                override fun onNext(t: Array<MenuEntity>) {
                    //判断成功失败
                    mMvpView.initMenuData(t)
                }

                override fun onError(e: Throwable?) {
                    //发生异常
                }

                override fun onCompleted() {
                    mMvpView.hideLoading()
                }
            })
        }else{

        }
    }

}
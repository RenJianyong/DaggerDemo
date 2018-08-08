package com.rjy.dagger.base.mvp

/**
 * 业务处理，Presenter基类
 */
abstract class MvpBasePresenter<T : MvpView,E:BaseEngine> (mvpView:T) {
    lateinit var mMvpView: T
    lateinit var mEngine:E

    init {
        this.mMvpView = mvpView
        val clazz = initEngine()
        val constructor = clazz.getConstructor()
        mEngine = constructor.newInstance()
    }

    /**
     * 返回Engine类型Class
     */
    abstract fun initEngine():Class<E>
}
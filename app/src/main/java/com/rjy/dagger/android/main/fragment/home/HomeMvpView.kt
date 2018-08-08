package com.rjy.dagger.android.main.fragment.home

import com.rjy.dagger.android.main.entity.MenuEntity
import com.rjy.dagger.base.mvp.MvpView

interface HomeMvpView : MvpView {

    fun initMenuData(array: Array<MenuEntity>)

}
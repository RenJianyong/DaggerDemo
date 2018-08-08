package com.rjy.dagger.android.main.entity

import com.rjy.dagger.base.mvp.BaseEngine

class MenuEntity : BaseEngine() {
/*
    "meunLevel":"0",
    "menuName":"超市",
    "menuStatus":"1",
    "menuStatusLabel":"正常",
    "menuId":"0001",
    "menuParId":"0000",
    "menuImg":"data/img/menu/01.png",
    "menuUrl":""
    */
    lateinit var meunLevel:String
    lateinit var menuName:String
    lateinit var menuStatus:String
    lateinit var menuStatusLabel:String
    lateinit var menuId:String
    lateinit var menuParId:String
    lateinit var menuImg:String
    lateinit var menuUrl:String

}
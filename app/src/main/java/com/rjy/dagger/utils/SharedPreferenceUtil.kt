package com.rjy.dagger.utils

import android.content.Context
import android.content.SharedPreferences


/**
 * SharedPreference工具类
 */
class SharedPreferenceUtil private constructor(context: Context) {

    private var SP_NAME: String
    private var sp: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        SP_NAME = context.packageName
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        editor = sp.edit()
    }

    companion object {
        private var instance:SharedPreferenceUtil? = null
        fun getInstance(context: Context): SharedPreferenceUtil? {
            if (instance == null){
                synchronized(SharedPreferenceUtil::class){
                    if (instance == null){
                        instance = SharedPreferenceUtil(context)
                    }
                }
            }
            return instance
        }
    }


    /**
     * 添加String
     * @Description
     * @param key
     * @param value
     */
    fun setString(key: String, value: String) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    /**
     * 获取String
     * @Description
     * @param key
     * @param defValue 默认值
     * @return
     */
    @JvmOverloads
    fun getString(key: String, defValue: String? = null): String? {
        return sp!!.getString(key, defValue)
    }

    /**
     * 添加Int
     * @Description
     * @param key
     * @param value
     */
    fun setInt(key: String, value: Int) {
        editor!!.putInt(key, value)
        editor!!.commit()
    }


    /**
     * 获取Int
     * @Description
     * @param key
     * @param defValue 默认值
     * @return
     */
    @JvmOverloads
    fun getInt(key: String, defValue: Int = 0): Int {
        return sp!!.getInt(key, defValue)
    }


    /**
     * 添加float
     * @Description
     * @param key
     * @param value
     */
    fun setFloat(key: String, value: Float) {
        editor!!.putFloat(key, value)
        editor!!.commit()
    }

    /**
     * 获取float
     * @Description
     * @param key
     * @param defValue 默认值
     * @return
     */
    @JvmOverloads
    fun getFloat(key: String, defValue: Float = 0.0f): Float {
        return sp!!.getFloat(key, defValue)
    }

    /**
     * 添加boolean
     * @Description
     * @param key
     * @param value
     */
    fun setBoolean(key: String, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.commit()
    }

    /**
     * 获取boolean
     * @Description
     * @param key
     * @param defValue
     * @return
     */
    @JvmOverloads
    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return sp!!.getBoolean(key, defValue)
    }


    /**
     * 删除
     * @Description
     * @param key
     */
    fun delContent(key: String) {
        editor!!.remove(key)
        editor!!.commit()
    }




}
/**
 * 获取String
 * @Description
 * @param key
 * @return
 */
/**
 * 获取Int
 * @Description
 * @param key
 * @return
 */
/**
 * 获取float
 * @Description
 * @param key
 * @return
 */
/**
 * 获取boolean
 * @Description
 * @param key
 * @return
 */

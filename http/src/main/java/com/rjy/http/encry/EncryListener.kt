package com.rjy.http.encry

/**
 * 加解密接口
 * @author 任建勇
 */
interface EncryListener {

    /**
     * 加密
     * @param data 明文
     * @return 密文
     */
    fun encry(data:String):String

    /**
     * 解密
     * @param data 密文
     * @return 明文
     */
    fun decry(data: String):String

}
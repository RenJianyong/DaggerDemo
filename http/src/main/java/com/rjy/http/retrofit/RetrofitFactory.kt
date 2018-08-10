package com.rjy.http.retrofit

import com.rjy.http.constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class RetrofitFactory private constructor(){
    companion object {
        val instance: RetrofitFactory by lazy {
            RetrofitFactory()
        }
    }

    private val retrofit: Retrofit

    init {

        val httpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .sslSocketFactory(getFactorySSL())
                .hostnameVerifier(object : HostnameVerifier {
                    override fun verify(p0: String?, p1: SSLSession?): Boolean {
                        return true
                    }

                })
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .build()

    }

    fun <T> create(service:Class<T>):T{
        return retrofit.create(service)
    }

    private fun getFactorySSL(): SSLSocketFactory? {
        try {
            // 获取证书
            val stream = RetrofitFactory::class.java.getClassLoader().getResourceAsStream("assets/cgb.cer")

            val tls = SSLContext.getInstance("TLS")
            // 使用默认证书
            val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
            // 去掉系统默认证书
            keystore.load(null)
            val certificate = CertificateFactory.getInstance("X.509").generateCertificate(stream)
            // 设置自己的证书
            keystore.setCertificateEntry("skxy", certificate)
            // 通过信任管理器获取一个默认的算法
            val algorithm = TrustManagerFactory.getDefaultAlgorithm()
            // 算法工厂创建
            val instance = TrustManagerFactory.getInstance(algorithm)
            instance.init(keystore)
            tls.init(null, instance.getTrustManagers(), null)
            return tls.getSocketFactory()
        } catch (e: Exception) {
            val xx = e.toString() + ""
            return null
        }

    }
}
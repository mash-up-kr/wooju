package com.mashup.lemonsatang

import android.app.Application
import com.kakao.auth.KakaoSDK
import com.mashup.lemonsatang.util.KakaoSdkAdapter


class MonndayApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
        // Kakao Sdk 초기화
        KakaoSDK.init(KakaoSdkAdapter())
    }

    companion object {
        private var instance: MonndayApplication? = null

        fun getMonndayApplicationContext(): MonndayApplication? {
            checkNotNull(instance) { "This Application does not inherit com.kakao.GlobalApplication" }
            return instance
        }
    }
}
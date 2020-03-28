package com.mashup.lemonsatang

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.auth.KakaoSDK
import com.mashup.lemonsatang.di.networkModule
import com.mashup.lemonsatang.di.repositoryModule
import com.mashup.lemonsatang.di.viewModelModule
import com.mashup.lemonsatang.util.KakaoSdkAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MonndayApplication : Application() {

    private val moduleList = listOf(repositoryModule, networkModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initKoin()
        initKakaoSdk()
        initFirebaseApp()
    }

    private fun initKoin(){
        startKoin {
            androidContext(this@MonndayApplication)
            modules(moduleList)
        }
    }

    private fun initKakaoSdk(){
        KakaoSDK.init(KakaoSdkAdapter())
    }

    private fun initFirebaseApp(){
        FirebaseApp.initializeApp(this)
    }

    companion object {
        private var INSTANCE: MonndayApplication? = null

        fun getMonndayApplicationContext(): MonndayApplication? {
            checkNotNull(INSTANCE) { "This Application does not inherit com.kakao.GlobalApplication" }
            return INSTANCE
        }
    }
}
package com.mashup.lemonsatang.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.kakao.auth.Session
import com.linecorp.apng.ApngDrawable
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.prefs.SharedPreferenceStorage
import com.mashup.lemonsatang.databinding.ActivitySplashBinding
import com.mashup.lemonsatang.di.VAL_AUTORIZATION
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.ui.login.LoginActivity
import com.mashup.lemonsatang.ui.main.MainActivity
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val pref : SharedPreferenceStorage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToken()
        initMotionLayout()
        checkUserLoginAndRedirect()
    }

    private fun setupToken(){
        VAL_AUTORIZATION = pref.token
    }

    private fun initMotionLayout() {
        binding.motionlayout.transitionToEnd()
    }

    private fun checkUserLoginAndRedirect() {
        val intent = when (checkUserLogin()) {
            true -> Intent(this, MainActivity::class.java)
            false -> Intent(this, LoginActivity::class.java)
        }

        redirectWithDelay(intent)
    }

    // 사용자가 이전에 앱 사용시 어떤 로그인 연동을 했는지 로컬에 데이터 가지고 있어야함
    // 해당 데이터를 가지고 각 플랫폼별 자동로그인 로직구현 필요
    private fun checkUserLogin(): Boolean {
        val token = pref.token
        return token.isNotEmpty()
    }

    private fun redirectWithDelay(intent: Intent) {
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, REDIRECT_DURATION)
    }

    companion object {
        private const val REDIRECT_DURATION = 4000L
    }
}
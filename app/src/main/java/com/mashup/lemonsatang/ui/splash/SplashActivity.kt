package com.mashup.lemonsatang.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.kakao.auth.Session
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivitySplashBinding
import com.mashup.lemonsatang.ui.login.LoginActivity
import com.mashup.lemonsatang.ui.main.MainActivity
import com.mashup.lemonsatang.util.getHashKey

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMotionLayout()
        checkUserLoginAndRedirect()
        Log.d("test", "hash: ${getHashKey(this)}")
    }

    private fun initMotionLayout(){
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
        // 카카오톡 로그인
        return !Session.getCurrentSession().isClosed
    }

    private fun redirectWithDelay(intent: Intent) {
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, REDIRECT_DURATION)
    }

    companion object {
        private const val REDIRECT_DURATION = 1500L
    }
}
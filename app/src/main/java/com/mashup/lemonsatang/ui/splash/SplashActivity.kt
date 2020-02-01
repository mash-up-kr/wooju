package com.mashup.lemonsatang.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.kakao.auth.Session
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivitySplashBinding
import com.mashup.lemonsatang.ui.login.LoginActivity
import com.mashup.lemonsatang.ui.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            redirectActivity()
        }, REDIRECT_DURATION)
    }

    private fun redirectActivity() {
        val intent = when (Session.getCurrentSession().isOpenable) {
            true -> Intent(this, MainActivity::class.java)
            false -> Intent(this, LoginActivity::class.java)
        }

        redirectActivityWithIntent(intent)
    }

    private fun redirectActivityWithIntent(intent: Intent) {
        Thread.sleep(REDIRECT_DURATION)

        startActivity(intent)
        finish()
    }

    companion object {
        private const val REDIRECT_DURATION = 1500L
    }
}
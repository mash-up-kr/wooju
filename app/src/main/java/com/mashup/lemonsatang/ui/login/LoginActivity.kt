package com.mashup.lemonsatang.ui.login

import android.content.Intent
import android.os.Bundle
import com.airbnb.lottie.LottieDrawable
import com.kakao.auth.Session
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityLoginBinding
import com.mashup.lemonsatang.ui.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val kakaoSessionCallback: KakaoSessionCallback by lazy { KakaoSessionCallback { redirectSignUpActivity() } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initKakaoLogin()
    }

    private fun initView() {
        showLottieAnimation()
    }

    private fun showLottieAnimation() {
        with(binding.lottieLogin) {
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
    }

    private fun initKakaoLogin() {
        Session.getCurrentSession().addCallback(kakaoSessionCallback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(kakaoSessionCallback)
    }

    private fun redirectSignUpActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
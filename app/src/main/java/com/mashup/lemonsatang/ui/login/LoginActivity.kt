package com.mashup.lemonsatang.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.kakao.auth.ApiResponseCallback
import com.kakao.auth.AuthService
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.auth.network.response.AccessTokenInfoResponse
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityLoginBinding
import com.mashup.lemonsatang.ui.main.MainActivity


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val callback: SessionCallback by lazy { SessionCallback() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLottieAnimation()

        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    private fun showLottieAnimation() {
        with(binding.lottieLogin) {
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    fun redirectSignUpActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    inner class SessionCallback : ISessionCallback {

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Log.d("test", exception.toString())
            }
        }

        override fun onSessionOpened() {
//            AuthService.getInstance()
//                .requestAccessTokenInfo(object : ApiResponseCallback<AccessTokenInfoResponse>() {
//                    override fun onSuccess(result: AccessTokenInfoResponse?) {
//                        result?.let {
//                            Log.d("test", "userId : ${it.userId}")
//                        }
//                    }
//
//                    override fun onSessionClosed(errorResult: ErrorResult?) {
//                        Log.d("test", "onSessionClosed.")
//                    }
//                })


            UserManagement.getInstance().me(object: MeV2ResponseCallback() {
                override fun onSuccess(result: MeV2Response?) {
                    result?.let {
                        Log.d("test", "userId : ${it.id}")
                        Log.d("test", "signup? : ${it.hasSignedUp()}")
                        Log.d("test", "kakaoaccount : ${it.kakaoAccount}")
                        Log.d("test", "group user token : ${it.groupUserToken}")
                        Log.d("test", "synchedAt : ${it.synchedAt}")
                    }
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    Log.d("test", "onSessionClosed.")
                }
            })

            redirectSignUpActivity()
        }
    }

}
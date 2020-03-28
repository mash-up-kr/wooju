package com.mashup.lemonsatang.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.kakao.auth.Session
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.data.prefs.SharedPreferenceStorage
import com.mashup.lemonsatang.databinding.ActivityLoginBinding
import com.mashup.lemonsatang.di.VAL_AUTORIZATION
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.ui.main.MainActivity
import com.mashup.lemonsatang.util.extension.showToast
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val repository: MonndayRepository by inject()
    private val pref: SharedPreferenceStorage by inject()
    private val kakaoSessionCallback: KakaoSessionCallback by lazy {
        KakaoSessionCallback(::redirect)
    }

    private var fcmToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getFcmToken()
        initMotionLayout()
        initEvent()
        initKakaoLogin()
    }

    private fun initMotionLayout() {
        binding.loginMotionBase.transitionToEnd()
    }

    private fun initEvent() {
        binding.btKakao.setOnClickListener {
            binding.kakaoLogin.performClick()
        }

        binding.btFacebook.setOnClickListener {
            showToast("추후에 지원될 예정입니다.")
        }

        binding.btInstagram.setOnClickListener {
            VAL_AUTORIZATION = "spacedeploy123456"
            pref.token = VAL_AUTORIZATION
            redirectMainActivity()
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

    private fun redirect(accessToken: String, refreshToken: String) {
        if (fcmToken != null) {
            VAL_AUTORIZATION = accessToken

            repository.login(fcmToken!!, {
                redirectMainActivity()
                pref.token = VAL_AUTORIZATION
            }, {
                VAL_AUTORIZATION = refreshToken
                repository.login(fcmToken!!, {
                    redirectMainActivity()
                    pref.token = VAL_AUTORIZATION
                }, {
                    showToast("로그인에 실패했습니다.")
                    VAL_AUTORIZATION = ""
                })
            })
        } else {
            showToast("FCM 토큰을 가져오는데 실패했습니다.")
        }
    }

    private fun redirectMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getFcmToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("test", "FCM 토큰 가져오기 실패", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                fcmToken = task.result?.token
                Log.d("test", "FCM 토큰 가져오기 성공 : $fcmToken")
            })
    }
}
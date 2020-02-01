package com.mashup.lemonsatang.ui.login

import android.util.Log
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException

class KakaoSessionCallback(val redirect: (isLoginSuccessful: Boolean) -> Unit) : ISessionCallback {

    override fun onSessionOpenFailed(exception: KakaoException?) {
        exception?.let { Log.d("test", exception.toString()) }
    }

    override fun onSessionOpened() {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onSuccess(result: MeV2Response?) {
                Log.d("test", "accessToken : ${Session.getCurrentSession().tokenInfo.accessToken}")
                Log.d(
                    "test",
                    "refreshToken : ${Session.getCurrentSession().tokenInfo.refreshToken}"
                )

                redirect(true)
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                Log.d("test", "onSessionClosed.")
                redirect(false)
            }
        })
    }
}
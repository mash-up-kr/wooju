package com.mashup.lemonsatang.ui.login

import android.util.Log
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException

class KakaoSessionCallback(
    private val redirect: (accessToken: String, refreshToken: String) -> Unit
) : ISessionCallback {

    override fun onSessionOpenFailed(exception: KakaoException?) {
        Log.d("test", "onSessionOpenFailed : $exception")
    }

    override fun onSessionOpened() {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onSuccess(result: MeV2Response?) {
                val session = Session.getCurrentSession()
                val accessToken = session.tokenInfo.accessToken
                val refreshToken = session.tokenInfo.refreshToken
                Log.d("test", "accessToken : ${session.tokenInfo.accessToken}")
                Log.d("test", "refreshToken : ${session.tokenInfo.refreshToken}")

                redirect(accessToken, refreshToken)
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                Log.d("test", "onSessionClosed.")
            }
        })
    }
}
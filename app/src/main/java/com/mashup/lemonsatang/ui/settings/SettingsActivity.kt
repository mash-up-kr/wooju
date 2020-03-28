package com.mashup.lemonsatang.ui.settings

import android.content.Intent
import android.os.Bundle
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.prefs.SharedPreferenceStorage
import com.mashup.lemonsatang.databinding.ActivitySettingsBinding
import com.mashup.lemonsatang.di.VAL_USER_EMAIL
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.ui.login.LoginActivity
import com.mashup.lemonsatang.ui.settings.event.SettingEventListener
import com.mashup.lemonsatang.ui.settings.license.LicenseActivity
import com.mashup.lemonsatang.util.extension.showToast
import kotlinx.android.synthetic.main.toolbar_remind_list.*
import org.koin.android.ext.android.inject


class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings),
    SettingEventListener {

    private val pref : SharedPreferenceStorage by inject()

    private var isAlarmOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.eventListener = this

        initToolbar()
        setupTvAccountSettings()

    }

    private fun initToolbar() {
        tvTitleToolbarRemind.text = getString(R.string.toolbar_settings_title)
        btnBackToolbarRemind.setOnClickListener { onBackPressed() }
    }

    private fun setupTvAccountSettings() {
        binding.tvAccountSettings.text = VAL_USER_EMAIL
    }

    override fun onNotificationClicked() {
        when (isAlarmOn) {
            true -> {
                isAlarmOn = false
                binding.switchAlarm.performClick()
                showToast("알람이 해제되었습니다.")
            }
            false -> {
                isAlarmOn = true
                binding.switchAlarm.performClick()
                showToast("알람이 설정되었습니다.")
            }
        }
    }

    override fun onOpenSourceLicenseClicked() {
        val intent = Intent(this, LicenseActivity::class.java)
        startActivity(intent)
    }

    override fun onDonationClicked() {
        showToast("추후에 지원될 예정입니다.")
    }

    override fun onAppReviewClicked() {
        showToast("추후에 지원될 예정입니다.")
    }

    override fun onLogOutClicked() {
        clearToken()

        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                val intent = Intent(this@SettingsActivity, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                showToast("로그아웃 되었습니다.")
            }
        })
    }

    private fun clearToken(){
        pref.token = ""
    }
}

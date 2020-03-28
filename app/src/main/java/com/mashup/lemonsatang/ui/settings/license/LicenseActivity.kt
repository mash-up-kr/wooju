package com.mashup.lemonsatang.ui.settings.license

import android.os.Bundle
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.databinding.ActivityLicenseBinding
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mikepenz.aboutlibraries.LibsBuilder

class LicenseActivity : BaseActivity<ActivityLicenseBinding>(R.layout.activity_license){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionEvent()
        setupLicenseView()
    }

    private fun setupLicenseView() {
        val fragment = LibsBuilder()
            .withLicenseShown(true)
            .withAboutIconShown(true)
            .withAboutAppName(getString(R.string.app_name))
            .supportFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    private fun setupActionEvent(){
        binding.toolbar.btBack.setOnClickListener {
            onBackPressed()
        }
    }

}


package com.mashup.lemonsatang.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.lemonsatang.R
import kotlinx.android.synthetic.main.toolbar_remind_list.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initToolbar()
    }


    private fun initToolbar(){
        tvTitleToolbarRemind.text = getString(R.string.toolbar_settings_title)
        btnBackToolbarRemind.setOnClickListener { onBackPressed() }
    }
}

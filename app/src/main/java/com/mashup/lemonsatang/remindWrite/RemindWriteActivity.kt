package com.mashup.lemonsatang.remindWrite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.ui.remindlist.RemindListAdapter
import kotlinx.android.synthetic.main.activity_remind_write.*
import kotlinx.android.synthetic.main.toolbar_remind_list.*

class RemindWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remind_write)

        init()
        toolbarClick()
    }

    private fun init(){
        tvDateRemindWrite.text = RemindListAdapter.date
    }
    private fun toolbarClick(){
        etContentRemindWrite.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val intent = Intent(this, RemindWriteContentActivity::class.java)
                startActivity(intent)
                overridePendingTransition(
                    R.anim.slide_bottom_in,
                    R.anim.slide_top_out
                )
                etContentRemindWrite.clearFocus()
            }
        }

        btnBackToolbarRemind.setOnClickListener {
            onBackPressed()
        }
    }

}

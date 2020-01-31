package com.mashup.lemonsatang.ui.dailywrite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.mashup.lemonsatang.R
import kotlinx.android.synthetic.main.activity_daily_write.*
import kotlinx.android.synthetic.main.activity_daily_write_content.*
import kotlinx.android.synthetic.main.activity_daily_write_content.et_daily_content
import kotlinx.android.synthetic.main.activity_daily_write_content.tv_submit

class DailyWriteContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_write_content)

        init()
    }

    private fun init(){
        btnSubmitClick()
        btnBackClick()
    }

    private fun btnSubmitClick(){
        tv_submit.setOnClickListener {
            var intent = Intent(this, DailyWriteActivity::class.java)
            var dailyContent = et_daily_content.text.toString()

            val builder = MaterialDialog(this).show{
                message (text = "작성 완료되었습니다.")
                positiveButton (text="확인"){
                    intent.putExtra("daily_write_content",dailyContent)
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }
        }
    }

    private fun btnBackClick(){
        btn_cancel.setOnClickListener {
            onBackPressed()
        }
    }
}

package com.mashup.lemonsatang.ui.dailywrite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.mashup.lemonsatang.R
import kotlinx.android.synthetic.main.activity_daily_write_content.*

class DailyWriteContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_write_content)

        init()
    }

    private fun init(){
        initDailyContent()
        clickBtnSubmit()
        clickBtnBack()
    }

    private fun initDailyContent(){
        et_daily_content.apply {
            val content = intent.getStringExtra("dailyContent") ?: ""
            if (!content.isBlank()) {
                setText(content)
                setSelection(text.toString().length)
            }

            requestFocus()
        }
    }

    private fun clickBtnSubmit(){
        tv_submit.setOnClickListener {
            var intent = Intent().apply {
                var dailyContent = et_daily_content.text.toString()
                putExtra("daily_write_content",dailyContent)
            }
            setResult(RESULT_OK,intent)
            finish()

//            MaterialDialog(this).show{
//                message (text = "작성 완료되었습니다.")
//                positiveButton (text="확인"){
//                    intent.putExtra("daily_write_content",dailyContent)
//                    setResult(RESULT_OK,intent)
//                    finish()
//                }
//            }
        }
    }

    private fun clickBtnBack(){
        btn_cancel.setOnClickListener {
            onBackPressed()
        }
    }
}

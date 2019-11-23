package com.mashup.lemonsatang.ui.dailywrite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.mashup.lemonsatang.R
import kotlinx.android.synthetic.main.activity_daily_write_content.*

class DailyWriteContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_write_content)

        tv_submit.setOnClickListener {
            val builder = MaterialDialog(this).show{
                message (text = "작성 완료되었습니다.")
                positiveButton (text="확인")
            }
        }

    }
}

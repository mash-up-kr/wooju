package com.mashup.lemonsatang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_daily_write_content.*

class DailyWriteContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_write_content)

        tv_submit.setOnClickListener {
            val builder = MaterialDialog(this).show{
                message (text = "Dialog")
                positiveButton (text="작성 완료되었습니다.")
            }
        }

    }
}

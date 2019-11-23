package com.mashup.lemonsatang

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_daily_write.*


class DailyWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_write)

        et_daily_content.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val intent = Intent(this, DailyWriteContentActivity::class.java)
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);

                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);

                overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
                et_daily_content.clearFocus()
            }
        }
    }
}

package com.mashup.lemonsatang.remindWrite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.ui.remindlist.RemindListAdapter
import com.mashup.lemonsatang.ui.remindlist.RemindListDate
import kotlinx.android.synthetic.main.toolbar_remind_list.*
import java.text.SimpleDateFormat
import java.util.*

class RemindWriteContentActivity : AppCompatActivity(), RemindListDate{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remind_write_content)

        toolbarClick()
    }

    private fun toolbarClick(){
        tvCompleteToolbarRemind.visibility = View.VISIBLE
        tvCompleteToolbarRemind.setOnClickListener {
            MaterialDialog(this).show{
                message (text = getString(R.string.toolbar_remind_dialog_msg,weekOfMonth(RemindListAdapter.startDate)))
                positiveButton (text="확인")
            }
        }

        btnBackToolbarRemind.setOnClickListener {
            onBackPressed()
        }
    }

}

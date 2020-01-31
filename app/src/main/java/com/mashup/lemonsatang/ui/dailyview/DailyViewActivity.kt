package com.mashup.lemonsatang.ui.dailyview

import android.content.Intent
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityDailyViewBinding
import com.mashup.lemonsatang.ui.dailywrite.DailyWriteActivity
import kotlinx.android.synthetic.main.activity_daily_view.*
import kotlinx.android.synthetic.main.bottom_sheet_daily_edit.*
import org.jetbrains.anko.toast

class DailyViewActivity : BaseActivity<ActivityDailyViewBinding>(R.layout.activity_daily_view) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init(){
        clickBtnEdit()
        clickBtnBack()
    }

    private fun clickBtnEdit(){
        daily_view_btn_edit.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_daily_edit, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(dialogView)
            bottomDialogClick(dialog)
            dialog.show()
        }
    }

    private fun bottomDialogClick(dialog: BottomSheetDialog){
        val btnEdit = dialog.btn_daily_view_edit
        val btnDelete = dialog.btn_daily_view_delete

        btnEdit.setOnClickListener {
            var intent = Intent(this, DailyWriteActivity::class.java)
            intent.putExtra("daily_date","20191122") //날짜를 넘김
            startActivity(intent)
        }
        btnDelete.setOnClickListener {
            MaterialDialog(this).show{
                message (text = "정말 삭제하시겠습니까?")
                negativeButton (text = "아니오")
                positiveButton (text = "삭제"){
                    dialog.dismiss()
                    finish()
                }
            }
        }
    }

    private fun clickBtnBack(){
        btn_main_back.setOnClickListener {
            onBackPressed()
        }
    }
}

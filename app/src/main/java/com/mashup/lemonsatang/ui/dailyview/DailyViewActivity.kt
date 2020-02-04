package com.mashup.lemonsatang.ui.dailyview

import android.content.Intent
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityDailyViewBinding
import com.mashup.lemonsatang.ui.dailywrite.DailyWriteActivity
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_MONTH_KEY
import com.mashup.lemonsatang.ui.monthlylist.MonthlyListActivity.Companion.CURR_DAY_KEY
import kotlinx.android.synthetic.main.activity_daily_view.*
import kotlinx.android.synthetic.main.bottom_sheet_daily_edit.*

class DailyViewActivity : BaseActivity<ActivityDailyViewBinding>(R.layout.activity_daily_view) {

    private var day:Int? = null
    private var month:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init(){
        setToolbarDate()
        clickBtnEdit()
        clickBtnBack()
    }

    private fun setToolbarDate(){
        day = intent.getIntExtra(CURR_DAY_KEY,-1) //MonthlyListActivity 에서 넘어온 일
        month = intent.getIntExtra(CURR_MONTH_KEY,-1)  //MonthlyListActivity 에서 넘어온 월

        daily_view_date.text = "${month}월 ${day}일"
    }

    private fun clickBtnEdit(){
        daily_view_btn_edit.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_daily_edit, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(dialogView)
            clickBottomDialog(dialog)
            dialog.show()
        }
    }

    private fun clickBottomDialog(dialog: BottomSheetDialog){
        val btnEdit = dialog.btn_daily_view_edit
        val btnDelete = dialog.btn_daily_view_delete

        btnEdit.setOnClickListener {
            var intent = Intent(this, DailyWriteActivity::class.java)
            intent.putExtra(CURR_MONTH_KEY, month) //날짜를 넘김
            intent.putExtra(CURR_DAY_KEY, day)
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

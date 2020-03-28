package com.mashup.lemonsatang.ui.dailyview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.databinding.ActivityDailyViewBinding
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.ui.dailywrite.DailyWriteActivity
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_MONTH_KEY
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_YEAR_KEY
import com.mashup.lemonsatang.ui.monthlylist.MonthlyListActivity.Companion.CURR_DAY_KEY
import com.mashup.lemonsatang.util.extension.setEmotionApngDrawable
import kotlinx.android.synthetic.main.activity_daily_view.*
import kotlinx.android.synthetic.main.bottom_sheet_daily_edit.*
import org.koin.android.ext.android.inject

class DailyViewActivity : BaseActivity<ActivityDailyViewBinding>(R.layout.activity_daily_view) {

    private val repository : MonndayRepository by inject()

    private var year : Int = -1
    private var day:Int = -1
    private var month:Int = -1
    private var emotionId : Int = -1
    private var dailylogId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init(){
        setToolbarDate()
        clickBtnEdit()
        clickBtnBack()
        loadData()
    }

    private fun loadData(){
        repository.getDailyArticle(day,month,year,{
            tv_daily_content.text = it.article
            emotionId = it.emotion
            dailylogId = it.dailylogId!!

            iv_daily_view_emotion.setEmotionApngDrawable(emotionId)
        },{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setToolbarDate(){
        year = intent.getIntExtra(CURR_YEAR_KEY,-1) //MonthlyListActivity 에서 넘어온 년도
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
            var intent = Intent(this, DailyWriteActivity::class.java).apply {
                putExtra(CURR_MONTH_KEY, month) //날짜를 넘김
                putExtra(CURR_DAY_KEY, day)
                putExtra(CURR_YEAR_KEY, year)
                putExtra("emotionId", emotionId) // 감정 넘김
                putExtra("dailyContent", tv_daily_content.text.toString()) //내용 넘김
                putExtra("dailylogId", dailylogId) // 아티클 아이디 넘김
            }
            startActivityForResult(intent,1)
            dialog.dismiss()
        }
        btnDelete.setOnClickListener {
            MaterialDialog(this).show{
                message (text = "정말 삭제하시겠습니까?")
                negativeButton (text = "아니오")
                positiveButton (text = "삭제"){
                    deleteArticle() // 서버에서 데이터 삭제
                    dialog.dismiss()
                    finish()
                }
            }
        }
    }

    private fun deleteArticle(){
        repository.deleteDailyArticle(dailylogId,{},{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun clickBtnBack(){
        btn_main_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === Activity.RESULT_OK) {
            var updatedContent = data!!.getStringExtra("updatedContent")
            tv_daily_content.text = updatedContent
            iv_daily_view_emotion.setEmotionApngDrawable(data!!.getIntExtra("updatedEmotionId",0))
        }
    }
}

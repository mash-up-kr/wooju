package com.mashup.lemonsatang.ui.dailywrite

import android.annotation.TargetApi
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_MONTH_KEY
import com.mashup.lemonsatang.ui.monthlylist.MonthlyListActivity.Companion.CURR_DAY_KEY
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_daily_write.*
import java.util.*


class DailyWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mashup.lemonsatang.R.layout.activity_daily_write)

        init()
    }

    private fun init(){
        focusOnEtDailyContent()
        clickBtnBack()
        clickBtnSubmit()
        clickTvDailyDate()
        setRvAdapter()
        setInitialDate()
    }

    private fun setInitialDate(){
        var year = 2019 //임시
        var month = intent.getIntExtra(CURR_MONTH_KEY,-1)
        var day = intent.getIntExtra(CURR_DAY_KEY,-1)

        setToolbarAndDailyDate(year,month,day)
    }

    private fun setToolbarAndDailyDate(year : Int, month : Int, day : Int){
        //toolbar month 설정
        tv_daily_write_month.text = "${month}월"

        //daily date 설정
        tv_daily_date.text = "${month}월 ${day}일 ${convertToDayOfWeek(year,month-1,day)}요일"

        tv_daily_date.paintFlags = tv_daily_date.paintFlags or UNDERLINE_TEXT_FLAG
    }

    private fun setRvAdapter(){
        rv_daily_write.adapter = JellyEmotionAdapter()
        rv_daily_write.setOrientation(DSVOrientation.HORIZONTAL)
        rv_daily_write.setItemTransformer(
            ScaleTransformer.Builder()
            .setMinScale(0.6f)
            .build())
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun clickTvDailyDate(){
        //date picker
        val calendar = Calendar.getInstance()

        tv_daily_date.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                setToolbarAndDailyDate(year,month+1,day)
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)).show() // TODO : 해당 날짜로 초기화
        }
    }

    private fun convertToDayOfWeek(year : Int, month: Int, day:Int) : String?{
        var dayOfWeek : String? = null

        val calendar = Calendar.getInstance()
        calendar.set(year,month,day)

        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> dayOfWeek = "일"
            2 -> dayOfWeek = "월"
            3 -> dayOfWeek = "화"
            4 -> dayOfWeek = "수"
            5 -> dayOfWeek = "목"
            6 -> dayOfWeek = "금"
            7 -> dayOfWeek = "토"
        }

        return dayOfWeek
    }

    private fun focusOnEtDailyContent(){
        et_daily_content.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val intent = Intent(this, DailyWriteContentActivity::class.java)
                startActivityForResult(intent,1)
                overridePendingTransition(
                    com.mashup.lemonsatang.R.anim.slide_bottom_in,
                    com.mashup.lemonsatang.R.anim.slide_top_out
                )
                et_daily_content.clearFocus()
            }
        }
    }

    private fun clickBtnBack(){
        btn_main_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun clickBtnSubmit(){

        btn_submit.setOnClickListener {
            //날짜 저장
            // TODO : 파싱해주기
            var dailyWriteDate = tv_daily_date.text.toString()
            //내용 저장
            var dailyWriteContent = et_daily_content.text.toString()
            //감정 저장
            var dailyWriteEmotion = rv_daily_write.currentItem

            //TODO 서버전송

            val builder = MaterialDialog(this).show{
                message (text = "작성 완료되었습니다.")
                positiveButton (text="확인"){
                    finish()
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === Activity.RESULT_OK) {
            var dailyContent = data!!.getStringExtra("daily_write_content")
            et_daily_content.setText(dailyContent)
        } else {
            et_daily_content.setText("No Data")
        }
    }
}

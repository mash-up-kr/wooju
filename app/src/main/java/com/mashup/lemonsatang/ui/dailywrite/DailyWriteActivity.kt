package com.mashup.lemonsatang.ui.dailywrite

import android.annotation.TargetApi
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_daily_write.*
import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Paint
import android.os.Build
import android.util.Log
import java.util.*
import java.text.SimpleDateFormat
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.widget.TextView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.transform.ScaleTransformer


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
                var dayOfWeek = convertToDayOfWeek(year,month,day)

                tv_daily_date.text = "${month + 1}월 ${day}일 ${dayOfWeek}요일"
                tv_daily_write_month.text = "${month +1}월"

                tv_daily_date.paintFlags = tv_daily_date.paintFlags or UNDERLINE_TEXT_FLAG
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

            //서버전송



            finish()
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

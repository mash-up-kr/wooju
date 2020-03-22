package com.mashup.lemonsatang.ui.dailywrite

import android.annotation.TargetApi
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.ui.dailyview.DailyViewActivity
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_MONTH_KEY
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_YEAR_KEY
import com.mashup.lemonsatang.ui.monthlylist.MonthlyListActivity.Companion.CURR_DAY_KEY
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_daily_write.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class DailyWriteActivity : AppCompatActivity() {

    private val repository : MonndayRepository by inject()
    private var writeYear : Int = 0
    private var writeMonth : Int = 0
    private var writeDay : Int = 0
    private var isEdit : Boolean = false // 수정 view 인지 판단

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
        initializeData()
    }

    private fun initializeData(){
        setInitialDate() //날짜 초기화
        if(intent.getStringExtra("dailyContent")!=null){// DailyViewActivity 에서 edit 버튼을 눌러 들어온 경우
            et_daily_content.setText(intent.getStringExtra("dailyContent")) // 기록 내용 초기화
            rv_daily_write.layoutManager!!.scrollToPosition(intent.getIntExtra("emotionId",0)) // 감정 초기화
            isEdit=true
        }
    }

    private fun setInitialDate(){
        writeYear = intent.getIntExtra(CURR_YEAR_KEY,-1)
        writeMonth = intent.getIntExtra(CURR_MONTH_KEY,-1)
        writeDay = intent.getIntExtra(CURR_DAY_KEY,-1)

        setToolbarAndDailyDate()
    }

    private fun setToolbarAndDailyDate(){
        //toolbar month 설정
        tv_daily_write_month.text = "${writeMonth}월"

        //daily date 설정
        tv_daily_date.text = "${writeMonth}월 ${writeDay}일 ${convertToDayOfWeek(writeYear,writeMonth-1,writeDay)}요일"

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
        tv_daily_date.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                writeYear = year
                writeMonth = month+1
                writeDay = day
                setToolbarAndDailyDate()
            },intent.getIntExtra(CURR_YEAR_KEY,-1), intent.getIntExtra(CURR_MONTH_KEY,-1)-1,intent.getIntExtra(CURR_DAY_KEY,-1)).show()
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
                intent.putExtra("dailyContent", et_daily_content.text.toString())
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

            postOrPutData()

            MaterialDialog(this).show{
                message (text = "작성 완료되었습니다.")
                positiveButton (text="확인"){
                    finish()
                }
            }
        }

    }

    private fun postOrPutData(){

        //내용 저장
        var dailyWriteContent = et_daily_content.text.toString()
        //감정 저장
        var dailyWriteEmotion = rv_daily_write.currentItem

        if(!isEdit){
            // 새로운 기록인 경우 post
            val articleDto = Article(dailyWriteContent,null, dailyWriteEmotion, timeFormatter())

            repository.saveDailyArticle(articleDto,{},{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        }else{
            //수정 view 인 경우 put
            val articleDto = Article(dailyWriteContent,intent.getIntExtra("dailylogId",-1), dailyWriteEmotion, timeFormatter())

            repository.updateDailyArticle(articleDto,{
                var intent = Intent(this, DailyViewActivity::class.java)
                intent.putExtra("updatedContent",it.article)
                intent.putExtra("updatedEmotionId",it.emotion)
                setResult(RESULT_OK,intent)

            },{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        }

    }

    private fun timeFormatter() : String {
        val writeDate  = SimpleDateFormat("yyyy-MM-dd").parse("${writeYear}-${writeMonth}-${writeDay}")
        val currentTime = SimpleDateFormat("HH:mm:ss.SSS").format(Date()) // 지난 날짜의 기록을 남길 경우 현재 시간으로 설정했음
        val timeFormat = "${SimpleDateFormat("yyyy-MM-dd").format(writeDate)}T${currentTime}Z"

        return timeFormat
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === Activity.RESULT_OK) {
            var dailyContent = data!!.getStringExtra("daily_write_content")
            et_daily_content.setText(dailyContent)
        } else {
            //et_daily_content.setText("")
        }
    }
}

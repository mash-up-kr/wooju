package com.mashup.lemonsatang.ui.monthlylist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.databinding.ActivityMonthlyListBinding
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.ui.dailyview.DailyViewActivity
import com.mashup.lemonsatang.ui.dailywrite.DailyWriteActivity
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_MONTH_KEY
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_YEAR_KEY
import com.mashup.lemonsatang.ui.vo.MonthlyListItemVo
import kotlinx.android.synthetic.main.activity_monthly_list.*
import org.koin.android.ext.android.inject
import java.util.*


class MonthlyListActivity :
    BaseActivity<ActivityMonthlyListBinding>(com.mashup.lemonsatang.R.layout.activity_monthly_list) {

    private val repository : MonndayRepository by inject()

    private val monthlyListAdapter by lazy {
        MonthlyListAdapter { clickEventCallback(it) }
    }

    private fun clickEventCallback(position: Int) {
        val currData = monthlyListAdapter.getDataAt(position)

        // 현재 날짜(포지션)에 감정 기록이 있는지 없는지 체크하고
        // 있으면 daily_edit 화면으로
        // 없으면 daily_write 화면으로 이동한다.
        // 현재는 임시로 isDataSet 의 boolean 값으로 로직 설계
        when(currData.isDataSet){
            true -> startActivity(Intent(this, DailyViewActivity::class.java).apply {
                putExtra(CURR_YEAR_KEY, intent.getIntExtra(CURR_YEAR_KEY,-1))
                putExtra(CURR_DAY_KEY, position+1)
                putExtra(CURR_MONTH_KEY,intent.getIntExtra(CURR_MONTH_KEY,-1))
            })
            false -> startActivity(Intent(this, DailyWriteActivity::class.java).apply{
                putExtra(CURR_YEAR_KEY, intent.getIntExtra(CURR_YEAR_KEY,-1))
                putExtra(CURR_DAY_KEY, position + 1)
                putExtra(CURR_MONTH_KEY,intent.getIntExtra(CURR_MONTH_KEY,-1))
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init(){
        initRecyclerView()
        loadData()
        clickBtnBack()
        setToolbarMonth()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setToolbarMonth(){
        var month = intent.getIntExtra(CURR_MONTH_KEY,-1)
        tv_monthly_list_month.text = "${month}월"
    }

    private fun clickBtnBack(){
        btn_monthly_list_back.setOnClickListener {
            onBackPressed()
        }
    }


    private fun initRecyclerView() {
        binding.rvMonthlyList.adapter = monthlyListAdapter
    }

    private fun loadData(){

        val list = mutableListOf<MonthlyListItemVo>()
        var existEmotionIdArray = Array(32) { -1 } //index 날짜에 해당하는 emotionId array

        repository.getHomeData(intent.getIntExtra(CURR_YEAR_KEY,-1), {
            it.year?.forEach { year ->
                if (year.month == intent.getIntExtra(CURR_MONTH_KEY, -1)) {
                    // 해당 월의 감정 리스트만 가져온다.
                    year.emotionList?.forEach { emotion ->
                        existEmotionIdArray[emotion.day] = emotion.emotion // 최근 emotion iD 저장
                    }

                    for (i in 1..endOfMonth(intent.getIntExtra(CURR_YEAR_KEY, -1), year.month)) {
                        if (existEmotionIdArray[i] == -1) {
                            list.add(MonthlyListItemVo(i, -1,false))
                        } else {
                            list.add(MonthlyListItemVo(i, existEmotionIdArray[i], true))
                            println("감정아이디"+existEmotionIdArray[14]) //
                        }
                    }
                }
            }

            monthlyListAdapter.setData(list)
        },{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    //매월 마지막 날짜 구하기
    private fun endOfMonth(year:Int,month:Int): Int {
        //val dateFormat = SimpleDateFormat("yyyy-MM")
        val cal = Calendar.getInstance()
        cal.set(year, month - 1,1)

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    companion object {
        const val CURR_DAY_KEY = "CURR_DAY_KEY"
    }

}

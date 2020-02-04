package com.mashup.lemonsatang.ui.monthlylist

import android.content.Intent
import android.os.Bundle
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityMonthlyListBinding
import com.mashup.lemonsatang.ui.dailyview.DailyViewActivity
import com.mashup.lemonsatang.ui.dailywrite.DailyWriteActivity
import com.mashup.lemonsatang.ui.main.MainActivity.Companion.CURR_MONTH_KEY
import com.mashup.lemonsatang.ui.vo.MonthlyListItemVo
import kotlinx.android.synthetic.main.activity_monthly_list.*

class MonthlyListActivity :
    BaseActivity<ActivityMonthlyListBinding>(R.layout.activity_monthly_list) {

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
                putExtra(CURR_DAY_KEY, position+1)
                putExtra(CURR_MONTH_KEY,intent.getIntExtra(CURR_MONTH_KEY,-1))
            })
            false -> startActivity(Intent(this, DailyWriteActivity::class.java).apply{
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

    //테스트 데이터 삽입
    private fun loadData(){
        val list = mutableListOf<MonthlyListItemVo>()
        for(i in 1..15){
            list.add(MonthlyListItemVo(i, true))
        }
        for(i in 16..30){
            list.add(MonthlyListItemVo(i, false))
        }
        monthlyListAdapter.setData(list)
    }

    companion object {
        const val CURR_DAY_KEY = "CURR_DAY_KEY"
    }

}

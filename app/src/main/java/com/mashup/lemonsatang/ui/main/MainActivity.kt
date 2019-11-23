package com.mashup.lemonsatang.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.PagerSnapHelper
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityMainBinding
import com.mashup.lemonsatang.ui.monthlylist.MonthlyListActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val entryPointerAdapter by lazy {
        EntryPointerAdapter { clickEventCallback(it) }
    }

    private fun clickEventCallback(position: Int) {
        startActivity(Intent(this, MonthlyListActivity::class.java).apply {
            putExtra(CURR_MONTH_KEY, position)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        initEvent()
        loadData()

    }

    private fun initRecyclerView() {
        with(binding.rvMonthlySummary) {
            adapter = entryPointerAdapter
            scrollToPosition(10)

            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }
    }

    private fun initEvent() {
//        binding.
    }

    private fun loadData() {
        val newData = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        entryPointerAdapter.setData(newData)
    }

    companion object {
        const val CURR_MONTH_KEY = "CURR_MONTH_KEY"
    }

}
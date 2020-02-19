package com.mashup.lemonsatang.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.PagerSnapHelper
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityMainBinding
import com.mashup.lemonsatang.ui.dailywrite.DailyWriteActivity
import com.mashup.lemonsatang.ui.monthlylist.MonthlyListActivity
import com.mashup.lemonsatang.ui.monthlylist.MonthlyListActivity.Companion.CURR_DAY_KEY
import com.mashup.lemonsatang.ui.remindlist.RemindListActivity
import com.mashup.lemonsatang.ui.settings.SettingsActivity
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val repository: MonndayRepository by inject()

    private val entryPointerAdapter by lazy {
        EntryPointerAdapter { clickEventCallback(it) }
    }

    private fun clickEventCallback(position: Int) {
        startActivity(Intent(this, MonthlyListActivity::class.java).apply {
            putExtra(CURR_MONTH_KEY, position + 1)
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

            PagerSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun initEvent() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, DailyWriteActivity::class.java).apply {
                val calendar = Calendar.getInstance()
                putExtra(CURR_YEAR_KEY, calendar.get(Calendar.YEAR))
                putExtra(CURR_MONTH_KEY, calendar.get(Calendar.MONTH) + 1)
                putExtra(CURR_DAY_KEY, calendar.get(Calendar.DATE))
            })
        }

        binding.tvSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.tvReminder.setOnClickListener {
            startActivity(Intent(this, RemindListActivity::class.java))
        }
    }

    private fun loadData() {
        repository
            .getHomeData(2020,
                {
                    entryPointerAdapter.setData(it.year)
                }, {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
    }

    companion object {
        const val CURR_MONTH_KEY = "CURR_MONTH_KEY"
        const val CURR_YEAR_KEY = "CURR_YEAR_KEY"
    }

}

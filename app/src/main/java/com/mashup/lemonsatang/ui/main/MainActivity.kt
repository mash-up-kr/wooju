package com.mashup.lemonsatang.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.databinding.ActivityMainBinding
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.ui.dailywrite.DailyWriteActivity
import com.mashup.lemonsatang.ui.monthlylist.MonthlyListActivity
import com.mashup.lemonsatang.ui.remindlist.RemindListActivity
import com.mashup.lemonsatang.ui.settings.SettingsActivity
import com.mashup.lemonsatang.util.EventObserver
import com.mashup.lemonsatang.util.dpToPx
import com.mashup.lemonsatang.util.extension.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()
    private val entryPointerAdapter by lazy { EntryPointerAdapter() }
    private val miniCalendarAdapter by lazy { MiniCalendarAdapter() }

    private var currYear = Calendar.getInstance(Locale.KOREA).get(Calendar.YEAR)
    private var currMonth = Calendar.getInstance(Locale.KOREA).get(Calendar.MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = mainViewModel

        initView()
        initEvent()
        initCallback()
    }

    private fun initView() {
        initRecyclerView()
        initSpinnerView()
    }

    private fun initRecyclerView() {
        with(binding.rvMonthlySummary) {
            adapter = entryPointerAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        currMonth =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1
                        mainViewModel.setCurrMonth(currMonth)
                    }
                }
            })

            PagerSnapHelper().attachToRecyclerView(this)
        }

        binding.rvMiniCalendar.apply {
            adapter = miniCalendarAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 7)
            addItemDecoration(MiniRvDecoration(dpToPx(4)))
        }

    }

    private fun initSpinnerView() {
        val yearItems = resources.getStringArray(R.array.year_list)
        val spinnerAdapter =
            ArrayAdapter(this, R.layout.spinner_item_layout, yearItems).apply {
                setDropDownViewResource(R.layout.spinner_item_layout_drop_down)
            }

        binding.spinnerDate.apply {
            adapter = spinnerAdapter
            setSelection(adapter.count - 1)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val year = (adapter.getItem(position) as String).toInt()
                    mainViewModel.loadData(year)
                }
            }
        }
    }

    private fun initEvent() {
        binding.tvSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.tvReminder.setOnClickListener {
            startActivity(Intent(this, RemindListActivity::class.java))
        }

        binding.rvMiniCalendarClick.setOnClickListener {
            Log.d("test", "clicked")
            startActivity(Intent(this, MonthlyListActivity::class.java).apply {
                putExtra(CURR_MONTH_KEY, currMonth)
                putExtra(CURR_YEAR_KEY, binding.spinnerDate.selectedItem.toString().toInt())
            })
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, DailyWriteActivity::class.java).apply {
                val calendar = Calendar.getInstance()
                putExtra(CURR_YEAR_KEY, calendar.get(Calendar.YEAR))
                putExtra(CURR_MONTH_KEY, calendar.get(Calendar.MONTH) + 1)
                putExtra(MonthlyListActivity.CURR_DAY_KEY, calendar.get(Calendar.DATE))
            })
        }
    }

    private fun initCallback() {
        mainViewModel.yearList.observe(this, Observer {
            binding.rvMonthlySummary.scrollToPosition(currMonth)
            mainViewModel.setCurrMonth(currMonth)
        })

        mainViewModel.currYear.observe(this, Observer { currYear ->
            binding.spinnerDate.setSelection(currYear - 2011)
            binding.rvMonthlySummary.scrollToPosition(currMonth - 1)
        })

        mainViewModel.currMonth.observe(this, Observer {
            currMonth = it
        })

        mainViewModel.currMonthData.observe(this, Observer {
            miniCalendarAdapter.setData(it)
        })

        mainViewModel.isRemindAvailable.observe(this, Observer {
            binding.tvReminder.setAlarmOn(it)
        })

        mainViewModel.toastMsg.observe(this, EventObserver {
            showToast(it)
        })
    }

    override fun onResume() {
        super.onResume()
        loadDataByYear(currYear)
    }

    private fun loadDataByYear(year: Int) {
        mainViewModel.loadData(year)
    }

    companion object {
        const val CURR_MONTH_KEY = "CURR_MONTH_KEY"
        const val CURR_YEAR_KEY = "CURR_YEAR_KEY"
    }

}

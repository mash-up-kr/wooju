package com.mashup.lemonsatang.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.ui.main.MiniCalendarAdapter.Companion.VIEW_TYPE_DATE
import com.mashup.lemonsatang.ui.main.MiniCalendarAdapter.Companion.VIEW_TYPE_DAY
import com.mashup.lemonsatang.ui.main.model.DateItem
import com.mashup.lemonsatang.ui.main.model.DayItem
import com.mashup.lemonsatang.ui.main.model.HomeItem
import com.mashup.lemonsatang.util.Event
import java.util.*

class MainViewModel(private val repository: MonndayRepository) : ViewModel() {

    private val _yearList = MutableLiveData<List<HomeDataResponse.Year>>()
    val yearList: LiveData<List<HomeDataResponse.Year>> get() = _yearList

    private val _currYear = MutableLiveData<Int>(2020)
    val currYear: LiveData<Int> get() = _currYear

    private val _currMonth = MutableLiveData<Int>(3)
    val currMonth: LiveData<Int> get() = _currMonth

    val currMonthData = Transformations.switchMap(currMonth) {
        val list = _yearList.value ?: emptyList()
        Log.d("test", "test : $list")
        when (list.isNotEmpty()) {
            true -> MutableLiveData(getCurrMonthData(list, it))
            false -> null
        }
    }

    private val _isRemindAvailable = MutableLiveData<Boolean>()
    val isRemindAvailable: LiveData<Boolean> get() = _isRemindAvailable

    private val _isDataAvailable = MutableLiveData<Boolean>()
    val isDataAvailable: LiveData<Boolean> get() = _isDataAvailable

    private val _toastMsg = MutableLiveData<Event<String>>()
    val toastMsg: LiveData<Event<String>> get() = _toastMsg

    fun loadData(year: Int) {
        repository.getHomeData(year,
            {
                _yearList.value = it.year
                _currYear.value = year
                _isDataAvailable.value = true
            }, {
                showToastMsg("기록한 일기가 없습니다.")
                _isDataAvailable.value = false
            })

        repository.getRemind({
            val result = it.remindList
            when (result.size == 1 && result.first().contents == null) {
                true -> _isRemindAvailable.value = false
                false -> _isRemindAvailable.value = true
            }
        }, {
            _isRemindAvailable.value = false
        })
    }

    fun setCurrMonth(currMonth: Int) {
        _currMonth.value = currMonth
    }

    private fun getCurrMonthData(
        list: List<HomeDataResponse.Year>,
        currMonth: Int
    ): List<HomeItem>? {
        return list.let {
            val rtnVal = mutableListOf<HomeItem>()
            rtnVal.add(HomeItem(VIEW_TYPE_DATE, DateItem("S")))
            rtnVal.add(HomeItem(VIEW_TYPE_DATE, DateItem("M")))
            rtnVal.add(HomeItem(VIEW_TYPE_DATE, DateItem("T")))
            rtnVal.add(HomeItem(VIEW_TYPE_DATE, DateItem("W")))
            rtnVal.add(HomeItem(VIEW_TYPE_DATE, DateItem("T")))
            rtnVal.add(HomeItem(VIEW_TYPE_DATE, DateItem("F")))
            rtnVal.add(HomeItem(VIEW_TYPE_DATE, DateItem("S")))

            val currYear = currYear.value ?: 2020

            Log.d("test", "currMonth : $currMonth")
            Log.d("test", "currYear : $currYear")
            Log.d("test", "startday : ${getStartDateOfMonth(currYear, currMonth)}")
            Log.d("test", "totaldat : ${getEndOfMonth(currYear, currMonth)}")

            for (i in 1 until getStartDateOfMonth(currYear, currMonth)) {
                rtnVal.add(HomeItem(VIEW_TYPE_DAY, DayItem(-2)))
            }

            val arr = arrayOfNulls<DayItem>(getEndOfMonth(currYear, currMonth))

            it[currMonth].emotionList?.forEach { day ->
                val index = day.day - 1

                if (index >= 0 && index < arr.size) {
                    arr[index] = DayItem(day.emotion)
                }
            }

            for (i in arr.indices) {
                when (arr[i] == null) {
                    true -> rtnVal.add(HomeItem(VIEW_TYPE_DAY, DayItem(-1)))
                    false -> rtnVal.add(HomeItem(VIEW_TYPE_DAY, arr[i]!!))
                }
            }

            rtnVal
        }

    }

    private fun showToastMsg(msg: String) {
        _toastMsg.value = Event(msg)
    }

    //첫 요일 구하기
    private fun getStartDateOfMonth(year: Int, month: Int): Int {
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, 1)
        return cal.get(Calendar.DAY_OF_WEEK)
    }

    //매월 마지막 날짜 구하기
    private fun getEndOfMonth(year: Int, month: Int): Int {
//        if (isLeapYear(year) == 1 && month == 2) {
//            return 29
//        }

        //val dateFormat = SimpleDateFormat("yyyy-MM")
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, 1)
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun isLeapYear(year: Int): Int {
        return if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return 1
                } else {
                    return 0
                }
            }
            return 1;
        } else
            return 0;

    }

}
package com.mashup.lemonsatang.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.util.Event

class MainViewModel(private val repository: MonndayRepository) : ViewModel() {

    val yearList = MutableLiveData<List<HomeDataResponse.Year>>()

    private val _currYear = MutableLiveData<Int>()
    val currYear : LiveData<Int> get() = _currYear

    private val _toastMsg = MutableLiveData<Event<String>>()
    val toastMsg: LiveData<Event<String>> get() = _toastMsg

    fun loadData(year: Int) {
        repository
            .getHomeData(year,
                {
                    yearList.value = it.year
                    _currYear.value = year
                }, {
                    _currYear.value = _currYear.value
                    showToastMsg(it)
                })
    }

    private fun showToastMsg(msg: String) {
        _toastMsg.value = Event(msg)
    }
}
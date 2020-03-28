package com.mashup.lemonsatang.ui.remindlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.data.vo.RemindListResponse
import com.mashup.lemonsatang.util.Event


class RemindListViewModel(private val repository: MonndayRepository) : ViewModel() {

    private val _remindListResponse = MutableLiveData<ArrayList<RemindListResponse.RemindList>>()
    val remindListResponse : LiveData<ArrayList<RemindListResponse.RemindList>> get() = _remindListResponse

    private val _isRemindAvailable = MutableLiveData<Boolean>()
    val isRemindAvailable: LiveData<Boolean> get() = _isRemindAvailable

    private val _toastMsg = MutableLiveData<Event<String>>()
    val toastMsg: LiveData<Event<String>> get() = _toastMsg

    fun loadData() {
        repository
            .getRemind(
                {
                    _isRemindAvailable.value = true
                    _remindListResponse.value = it.remindList
                },
                {
                    _isRemindAvailable.value = false
                }
            )
    }

    private fun showToastMsg(msg: String) {
        _toastMsg.value = Event(msg)
    }
}
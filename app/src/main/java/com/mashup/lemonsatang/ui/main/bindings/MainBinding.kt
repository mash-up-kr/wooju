package com.mashup.lemonsatang.ui.main.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.ui.main.EntryPointerAdapter

@BindingAdapter("setYearList")
fun RecyclerView.setYearList(yearList: List<HomeDataResponse.Year>?) {
    (adapter as? EntryPointerAdapter)?.setData(yearList)
}
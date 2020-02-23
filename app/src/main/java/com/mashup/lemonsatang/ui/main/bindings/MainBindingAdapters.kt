package com.mashup.lemonsatang.ui.main.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.ui.main.EntryPointerAdapter

@BindingAdapter("setMonthlyListItemSrc")
fun ImageView.setMonthlyListItemSrc(isDataSet: Boolean?) {
    if (isDataSet == null) return

    when (isDataSet) {
        true -> setImageResource(R.drawable.transguility)
        false -> setImageResource(R.drawable.rectangle_copy_13)
    }
}

@BindingAdapter("setYearList")
fun RecyclerView.setYearList(yearList : List<HomeDataResponse.Year>?){
    (adapter as? EntryPointerAdapter)?.setData(yearList)
}
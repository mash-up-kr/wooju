package com.mashup.lemonsatang.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mashup.lemonsatang.R

@BindingAdapter("setMonthlyListItemSrc")
fun ImageView.setMonthlyListItemSrc(isDataSet: Boolean?){
    if (isDataSet == null) return

    if(isDataSet){
        setImageResource(R.drawable.group_2)
    }
    else{
        setImageResource(R.drawable.rectangle_copy_13)
    }

}
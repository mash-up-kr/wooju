package com.mashup.lemonsatang.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mashup.lemonsatang.R

@BindingAdapter("setMonthlyListItemSrc")
fun ImageView.setMonthlyListItemSrc(emotionId: Int) {
    if (emotionId == null) return

    when (emotionId) {
        -1 -> setImageResource(R.drawable.rectangle_copy_13)
        else -> setEmotionApngDrawable(emotionId)
    }
}
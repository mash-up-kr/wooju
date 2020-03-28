package com.mashup.lemonsatang.util

import android.content.res.Resources
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.util.extension.setEmotionApngDrawable

@BindingAdapter("setEmotionApng")
fun ImageView.setEmotionApng(emotionId: Int?) {
    if (emotionId == null) return

    when (emotionId) {
        -1 -> setImageResource(R.drawable.rectangle_copy_13)
        else -> setEmotionApngDrawable(emotionId)
    }
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}
package com.mashup.lemonsatang.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.linecorp.apng.ApngDrawable
import com.mashup.lemonsatang.R
import java.util.*

fun Context.showToast(msg: String) {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT).apply { show() }

    val timer = Timer()
    val timerTask = object : TimerTask() {
        override fun run() {
            toast.cancel()
        }
    }
    timer.schedule(timerTask, 2000)
}

fun ImageView.setEmotionApngDrawable(emotionId: Int) {
    val apngDrawable = when(emotionId){
        0 -> R.raw.monnday_pleasure
        1 -> R.raw.monnday_happiness
        2 -> R.raw.monnday_tranquility
        3 -> R.raw.monnday_satisfaction
        4 -> R.raw.monnday_aggro
        5 -> R.raw.monnday_depressed
        6 -> R.raw.monnday_tired
        else -> R.raw.monnday_sadness
    }

    val drawable = ApngDrawable.decode(resources, apngDrawable)
    drawable.start()
    setImageDrawable(drawable)
}

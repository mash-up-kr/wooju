package com.mashup.lemonsatang.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.linecorp.apng.ApngDrawable
import java.util.*

fun Context.showToast(msg: String) {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT).apply { show() }

    val timer = Timer()
    val timerTask = object : TimerTask() {
        override fun run() {
            toast.cancel()
        }
    }
    timer.schedule(timerTask, 1000)
}

fun ImageView.setApngDrawable(@RawRes @DrawableRes resId: Int) {
    val drawable = ApngDrawable.decode(resources, resId)
    drawable.start()
    setImageDrawable(drawable)
}

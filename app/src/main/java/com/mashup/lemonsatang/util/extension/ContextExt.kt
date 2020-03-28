package com.mashup.lemonsatang.util.extension

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.util.*

fun Context.showToast(msg: String) {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT).apply {
        (view as ViewGroup).apply {
            with(this.getChildAt(0) as TextView) {
                gravity = Gravity.CENTER
            }
        }
    }.apply { show() }

    val timer = Timer()
    val timerTask = object : TimerTask() {
        override fun run() {
            toast.cancel()
        }
    }
    timer.schedule(timerTask, 2000)
}
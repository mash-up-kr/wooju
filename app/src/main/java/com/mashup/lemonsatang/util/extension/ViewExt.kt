package com.mashup.lemonsatang.util.extension

import android.widget.ImageView
import com.linecorp.apng.ApngDrawable
import com.mashup.lemonsatang.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ImageView.setEmotionApngDrawable(emotionId: Int) {
    setImageResource(R.color.black) // set placeholder

    val apngDrawable = when (emotionId) {
        0 -> R.raw.monnday_pleasure
        1 -> R.raw.monnday_happiness
        2 -> R.raw.monnday_tranquility
        3 -> R.raw.monnday_satisfaction
        4 -> R.raw.monnday_aggro
        5 -> R.raw.monnday_depressed
        6 -> R.raw.monnday_tired
        else -> R.raw.monnday_sadness
    }

    CoroutineScope(Dispatchers.IO).launch {
        val drawable = ApngDrawable.decode(resources, apngDrawable)
        withContext(Dispatchers.Main) {
            drawable.start()
            setImageDrawable(drawable)
        }
    }
}
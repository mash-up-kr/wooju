package com.mashup.lemonsatang.util.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.mashup.lemonsatang.R

class BadgeTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {
    private var isAlarmOn = false

    init {
        context?.let {
            val arr = context.obtainStyledAttributes(
                attrs,
                R.styleable.BadgeTextView
            )
            isAlarmOn = arr.getBoolean(R.styleable.BadgeTextView_isAlarmOn, false)
            arr.recycle()
        }

        setPadding(this.paddingLeft, this.paddingTop, this.paddingRight + 24, this.paddingBottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isAlarmOn) {
            drawBadge(canvas)
        }
    }

    private fun drawBadge(canvas: Canvas?) {
        val x = (measuredWidth - 16).toFloat()
        val y = 10F
        val radius = 6F
        val paint = Paint().apply {
            style = Paint.Style.FILL;
            color = ContextCompat.getColor(context, R.color.angro)
        }
        canvas?.drawCircle(x, y, radius, paint)
    }

    fun setAlarmOn(isAlarmOn: Boolean) {
        this.isAlarmOn = isAlarmOn
        invalidate()
    }
}
package com.mashup.lemonsatang.ui.monthlylist

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView


class MyGridLayoutManager(context: Context?, private val factor: Float) :
    GridLayoutManager(context, 3) {

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        val linearSmoothScroller: LinearSmoothScroller =
            object : LinearSmoothScroller(recyclerView.context) {
                override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                    return this.computeScrollVectorForPosition(targetPosition)
                }

                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    return super.calculateSpeedPerPixel(displayMetrics) * factor
                }
            }

        linearSmoothScroller.targetPosition = position / 10
        startSmoothScroll(linearSmoothScroller)
    }

}
package com.example.moviecatalogproject.presentation.main.movie.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.min

class CenterZoomLinearLayoutManager(
    context: Context,
    private val mShrinkDistance: Float = 1f,
    private val mShrinkAmount: Float = 0.2f,
    private val doOnScrollStateChange: (state: Int) -> Unit
) : LinearLayoutManager(context, HORIZONTAL, false) {

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            doOnScrollStateChange(RecyclerView.SCROLL_STATE_IDLE)
        } else if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
            doOnScrollStateChange(RecyclerView.SCROLL_STATE_DRAGGING)
        }
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleChildren()
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == HORIZONTAL) {
            super.scrollHorizontallyBy(dx, recycler, state).also {
                scaleChildren()
            }
        } else {
            0
        }
    }

    private fun scaleChildren() {
        val middlePoint = width / 2f
        val d1 = mShrinkDistance * middlePoint
        for (i in 0 until childCount) {
            val child = getChildAt(i) as View
            val d = min(
                d1,
                abs((getDecoratedLeft(child)) / 2f)
            )
            val scale = 1f - mShrinkAmount * d / d1
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}
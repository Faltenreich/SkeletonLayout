package com.faltenreich.skeletonview

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView

class SkeletonView(private val recyclerView: RecyclerView, @LayoutRes layoutResId: Int, itemCount: Int = 10) : Skeleton {

    private val originalAdapter = recyclerView.adapter
    private val skeletonAdapter = SkeletonAdapter(layoutResId, itemCount)

    val isShown = recyclerView.adapter == skeletonAdapter

    override fun show() {
        recyclerView.adapter = skeletonAdapter
    }

    override fun hide() {
        recyclerView.adapter = originalAdapter
    }
}
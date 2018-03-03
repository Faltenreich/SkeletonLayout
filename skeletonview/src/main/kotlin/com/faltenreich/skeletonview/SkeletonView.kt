package com.faltenreich.skeletonview

import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView

class SkeletonView(private val recyclerView: RecyclerView, @LayoutRes layoutResId: Int, itemCount: Int = 5, @ColorRes private val maskColorResId: Int = R.color.greyLight) : Skeleton {

    private val originalAdapter = recyclerView.adapter
    private val skeletonAdapter = SkeletonAdapter(layoutResId, itemCount, ContextCompat.getColor(recyclerView.context, maskColorResId))

    override fun show() {
        recyclerView.adapter = skeletonAdapter
    }

    override fun hide() {
        recyclerView.adapter = originalAdapter
    }

    override fun isShown() = recyclerView.adapter === skeletonAdapter
}
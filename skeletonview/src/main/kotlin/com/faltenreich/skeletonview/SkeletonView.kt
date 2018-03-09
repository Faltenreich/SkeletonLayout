package com.faltenreich.skeletonview

import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView

class SkeletonView(
        private val recyclerView: RecyclerView,
        @LayoutRes layoutResId: Int,
        itemCount: Int = 5,
        @ColorRes maskColorResId: Int = R.color.grey,
        cornerRadius: Float = 25f,
        showShimmer: Boolean = true,
        @ColorRes shimmerColor: Int =  maskColorResId,
        shimmerDurationInMillis: Long = 2000,
        shimmerAngle: Float = 100f
) : Skeleton {

    private val context by lazy { recyclerView.context }

    private val originalAdapter = recyclerView.adapter

    private val skeletonAdapter = SkeletonAdapter(
            layoutResId,
            itemCount,
            ContextCompat.getColor(context, maskColorResId),
            cornerRadius,
            showShimmer,
            ContextCompat.getColor(context, shimmerColor),
            shimmerDurationInMillis,
            shimmerAngle)

    var stateChangeListener: SkeletonStateChangeListener? = null

    override fun show() {
        recyclerView.adapter = skeletonAdapter
        stateChangeListener?.onShow()
    }

    override fun hide() {
        recyclerView.adapter = originalAdapter
        stateChangeListener?.onHide()
    }

    override fun isShown() = recyclerView.adapter === skeletonAdapter
}
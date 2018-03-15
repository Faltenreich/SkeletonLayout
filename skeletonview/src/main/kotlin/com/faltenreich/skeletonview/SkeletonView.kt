package com.faltenreich.skeletonview

import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import com.faltenreich.skeletonview.list.SkeletonRecyclerViewAdapter

class SkeletonView(
        private val recyclerView: RecyclerView,
        @LayoutRes layoutResId: Int,
        itemCount: Int = DEFAULT_ITEM_COUNT,
        @ColorRes maskColorResId: Int = SkeletonLayout.DEFAULT_MASK_COLOR,
        cornerRadius: Float = SkeletonLayout.DEFAULT_CORNER_RADIUS,
        showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
        @ColorRes shimmerColorResId: Int = SkeletonLayout.DEFAULT_SHIMMER_COLOR,
        shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS
) : Skeleton {

    private val originalAdapter = recyclerView.adapter

    private val skeletonAdapter = SkeletonRecyclerViewAdapter(
            layoutResId,
            itemCount,
            maskColorResId,
            cornerRadius,
            showShimmer,
            shimmerColorResId,
            shimmerDurationInMillis)

    override fun show() {
        recyclerView.adapter = skeletonAdapter
    }

    override fun hide() {
        recyclerView.adapter = originalAdapter
    }

    override fun isSkeleton() = recyclerView.adapter === skeletonAdapter

    companion object {
        private const val DEFAULT_ITEM_COUNT = 3
    }
}
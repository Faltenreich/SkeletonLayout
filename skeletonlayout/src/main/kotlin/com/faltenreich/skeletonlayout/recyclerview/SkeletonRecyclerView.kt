package com.faltenreich.skeletonlayout.recyclerview

import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import com.faltenreich.skeletonlayout.Skeleton

internal class SkeletonRecyclerView(
        private val recyclerView: RecyclerView,
        @LayoutRes layoutResId: Int,
        itemCount: Int,
        @ColorRes maskColorResId: Int,
        cornerRadius: Float,
        showShimmer: Boolean,
        @ColorRes shimmerColorResId: Int,
        shimmerDurationInMillis: Long
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

    override fun showOriginal() {
        recyclerView.adapter = originalAdapter
    }

    override fun showSkeleton() {
        recyclerView.adapter = skeletonAdapter
    }

    override fun isSkeleton() = recyclerView.adapter === skeletonAdapter

    companion object {
        const val DEFAULT_ITEM_COUNT = 3
    }
}
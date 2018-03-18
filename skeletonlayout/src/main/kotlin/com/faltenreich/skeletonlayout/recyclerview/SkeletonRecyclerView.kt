package com.faltenreich.skeletonlayout.recyclerview

import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
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

    var layoutResId: Int = layoutResId
        set(value) {
            field = value
            invalidate()
        }

    var itemCount: Int = itemCount
        set(value) {
            field = value
            invalidate()
        }

    override var maskColorResId: Int = maskColorResId
        set(value) {
            field = value
            maskColor = ContextCompat.getColor(recyclerView.context, value)
        }

    override var maskColor: Int = ContextCompat.getColor(recyclerView.context, maskColorResId)
        set(value) {
            field = value
            invalidate()
        }

    override var maskCornerRadius: Float = cornerRadius
        set(value) {
            field = value
            invalidate()
        }

    override var showShimmer: Boolean = showShimmer
        set(value) {
            field = value
            invalidate()
        }

    override var shimmerColorResId: Int = shimmerColorResId
        set(value) {
            field = value
            shimmerColor = ContextCompat.getColor(recyclerView.context, value)
        }

    override var shimmerColor: Int = ContextCompat.getColor(recyclerView.context, shimmerColorResId)
        set(value) {
            field = value
            invalidate()
        }

    override var shimmerDurationInMillis: Long = shimmerDurationInMillis
        set(value) {
            field = value
            invalidate()
        }

    private val originalAdapter = recyclerView.adapter

    private var skeletonAdapter: SkeletonRecyclerViewAdapter? = null

    init {
        invalidate()
    }

    override fun showOriginal() {
        recyclerView.adapter = originalAdapter
    }

    override fun showSkeleton() {
        recyclerView.adapter = skeletonAdapter
    }

    override fun isSkeleton() = recyclerView.adapter === skeletonAdapter

    private fun invalidate() {
        val showSkeleton = isSkeleton()
        skeletonAdapter = SkeletonRecyclerViewAdapter(
                layoutResId,
                itemCount,
                maskColorResId,
                maskCornerRadius,
                showShimmer,
                shimmerColorResId,
                shimmerDurationInMillis)
        if (showSkeleton) {
            showSkeleton()
        }
    }

    companion object {
        const val DEFAULT_ITEM_COUNT = 3
    }
}
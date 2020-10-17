package com.faltenreich.skeletonlayout.recyclerview

import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection

internal class SkeletonRecyclerView(
    private val recyclerView: RecyclerView,
    @LayoutRes layoutResId: Int,
    itemCount: Int,
    @ColorInt maskColor: Int,
    cornerRadius: Float,
    showShimmer: Boolean,
    @ColorInt shimmerColor: Int,
    shimmerDurationInMillis: Long,
    shimmerDirection: SkeletonShimmerDirection,
    shimmerAngle: Int
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

    override var maskColor: Int = maskColor
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

    override var shimmerColor: Int = shimmerColor
        set(value) {
            field = value
            invalidate()
        }

    override var shimmerDurationInMillis: Long = shimmerDurationInMillis
        set(value) {
            field = value
            invalidate()
        }

    override var shimmerDirection: SkeletonShimmerDirection = shimmerDirection
        set(value) {
            field = value
            invalidate()
        }

    override var shimmerAngle: Int = shimmerAngle
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

    override fun isSkeleton() = skeletonAdapter != null && recyclerView.adapter == skeletonAdapter

    private fun invalidate() {
        val showSkeleton = isSkeleton()
        skeletonAdapter = SkeletonRecyclerViewAdapter(
            layoutResId,
            itemCount,
            maskColor,
            maskCornerRadius,
            showShimmer,
            shimmerColor,
            shimmerDurationInMillis,
            shimmerDirection,
            shimmerAngle
        )
        if (showSkeleton) {
            showSkeleton()
        }
    }
}
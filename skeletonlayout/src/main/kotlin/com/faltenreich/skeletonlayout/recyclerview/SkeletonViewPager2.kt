package com.faltenreich.skeletonlayout.recyclerview

import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.viewpager2.widget.ViewPager2
import com.faltenreich.skeletonlayout.Skeleton

internal class SkeletonViewPager2(
    private val viewPager: ViewPager2,
    @LayoutRes layoutResId: Int,
    itemCount: Int,
    @ColorInt maskColor: Int,
    cornerRadius: Float,
    showShimmer: Boolean,
    @ColorInt shimmerColor: Int,
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

    private val originalAdapter = viewPager.adapter

    private var skeletonAdapter: SkeletonRecyclerViewAdapter? = null

    init {
        invalidate()
    }

    override fun showOriginal() {
        viewPager.adapter = originalAdapter
    }

    override fun showSkeleton() {
        viewPager.adapter = skeletonAdapter
    }

    override fun isSkeleton() = skeletonAdapter != null && viewPager.adapter == skeletonAdapter

    private fun invalidate() {
        val showSkeleton = isSkeleton()
        skeletonAdapter = SkeletonRecyclerViewAdapter(
            layoutResId,
            itemCount,
            maskColor,
            maskCornerRadius,
            showShimmer,
            shimmerColor,
            shimmerDurationInMillis)
        if (showSkeleton) {
            showSkeleton()
        }
    }
}
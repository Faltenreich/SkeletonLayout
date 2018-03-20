package com.faltenreich.skeletonlayout

import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.faltenreich.skeletonlayout.recyclerview.SkeletonRecyclerView

object SkeletonFactory {

    private const val LIST_ITEM_COUNT_DEFAULT = 3

    @JvmStatic
    @JvmOverloads
    fun skeletonForView(
            view: View,
            @ColorInt maskColor: Int = ContextCompat.getColor(view.context, SkeletonLayout.DEFAULT_MASK_COLOR),
            cornerRadius: Float = SkeletonLayout.DEFAULT_CORNER_RADIUS,
            showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
            @ColorInt shimmerColor: Int = ContextCompat.getColor(view.context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
            shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS
    ): Skeleton = SkeletonLayout(view, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis)

    @JvmStatic
    @JvmOverloads
    fun skeletonForView(
            recyclerView: RecyclerView,
            @LayoutRes layoutResId: Int,
            itemCount: Int = LIST_ITEM_COUNT_DEFAULT,
            @ColorInt maskColor: Int = ContextCompat.getColor(recyclerView.context, SkeletonLayout.DEFAULT_MASK_COLOR),
            cornerRadius: Float = SkeletonLayout.DEFAULT_CORNER_RADIUS,
            showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
            @ColorInt shimmerColor: Int = ContextCompat.getColor(recyclerView.context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
            shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS
    ): Skeleton = SkeletonRecyclerView(recyclerView, layoutResId, itemCount, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis)
}
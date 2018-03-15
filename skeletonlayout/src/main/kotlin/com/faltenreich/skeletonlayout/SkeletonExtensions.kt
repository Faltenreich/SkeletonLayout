@file:JvmName("SkeletonLayoutUtils")

package com.faltenreich.skeletonlayout

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.faltenreich.skeletonlayout.recyclerview.SkeletonRecyclerView
import java.util.*

internal fun Any.tag(): String = javaClass.simpleName

internal fun Context.refreshRateInSeconds(): Float = (getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.refreshRate ?: 60f

internal fun ViewGroup.views(): List<View> = (0 until childCount).map { getChildAt(it) }

internal fun View.isAttachedToWindowCompat() = ViewCompat.isAttachedToWindow(this)

internal fun Calendar.withTimeAtStartOfDay() = apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

@JvmOverloads
fun RecyclerView.applySkeletonAdapter(
        @LayoutRes layoutResId: Int,
        itemCount: Int = SkeletonRecyclerView.DEFAULT_ITEM_COUNT,
        @ColorRes maskColorResId: Int = SkeletonLayout.DEFAULT_MASK_COLOR,
        cornerRadius: Float = SkeletonLayout.DEFAULT_CORNER_RADIUS,
        showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
        @ColorRes shimmerColorResId: Int = SkeletonLayout.DEFAULT_SHIMMER_COLOR,
        shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS
): Skeleton = SkeletonRecyclerView(this, layoutResId, itemCount, maskColorResId, cornerRadius, showShimmer, shimmerColorResId, shimmerDurationInMillis)

@JvmOverloads
fun View.applySkeleton(
        @ColorRes maskColorResId: Int = SkeletonLayout.DEFAULT_MASK_COLOR,
        cornerRadius: Float = SkeletonLayout.DEFAULT_CORNER_RADIUS,
        showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
        @ColorRes shimmerColorResId: Int = SkeletonLayout.DEFAULT_SHIMMER_COLOR,
        shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS
): Skeleton = SkeletonLayout(this, maskColorResId, cornerRadius, showShimmer, shimmerColorResId, shimmerDurationInMillis)
package com.faltenreich.skeletonview

import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.faltenreich.skeletonview.list.SkeletonAdapter

class SkeletonView(
        private val recyclerView: RecyclerView,
        @LayoutRes layoutResId: Int,
        itemCount: Int = DEFAULT_ITEM_COUNT,
        @ColorRes maskColorResId: Int = DEFAULT_MASK_COLOR,
        cornerRadius: Float = DEFAULT_CORNER_RADIUS,
        showShimmer: Boolean = DEFAULT_SHIMMER_SHOW,
        @ColorRes shimmerColor: Int =  DEFAULT_SHIMMER_COLOR,
        shimmerDurationInMillis: Long = DEFAULT_SHIMMER_DURATION_IN_MILLIS,
        shimmerAngle: Float = DEFAULT_SHIMMER_ANGLE
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

    companion object {
        private const val DEFAULT_ITEM_COUNT = 5
        private val DEFAULT_MASK_COLOR = R.color.skeleton_mask
        private const val DEFAULT_CORNER_RADIUS = 25f
        private const val DEFAULT_SHIMMER_SHOW = true
        private val DEFAULT_SHIMMER_COLOR = R.color.skeleton_shimmer
        private const val DEFAULT_SHIMMER_DURATION_IN_MILLIS = 4000L
        private const val DEFAULT_SHIMMER_ANGLE = 100f
    }
}
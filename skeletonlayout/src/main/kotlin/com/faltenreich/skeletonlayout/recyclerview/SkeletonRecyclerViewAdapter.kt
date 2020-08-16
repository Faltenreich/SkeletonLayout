package com.faltenreich.skeletonlayout.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.faltenreich.skeletonlayout.createSkeleton

internal class SkeletonRecyclerViewAdapter(
        @LayoutRes private val layoutResId: Int,
        private var itemCount: Int,
        @ColorInt private val maskColor: Int,
        private val cornerRadius: Float,
        private val showShimmer: Boolean,
        @ColorInt private val shimmerColor: Int,
        private val shimmerDurationInMillis: Long
) : RecyclerView.Adapter<SkeletonRecyclerViewHolder>() {

    private var measured = itemCount != 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonRecyclerViewHolder {
        val originView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        val skeleton = originView.createSkeleton(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis) as SkeletonLayout
        skeleton.layoutParams = originView.layoutParams
        skeleton.showSkeleton()
        // Automatic calc the skeleton item count
        if (!measured) {
            var itemHeight = skeleton.height
            var parentHeight = parent.height
            val calcItemCount = {
                if (itemHeight != 0 && parentHeight != 0) {
                    measured = true
                    itemCount = parentHeight / itemHeight
                }
            }
            val listener = object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    if (top != bottom) {
                        skeleton.removeOnLayoutChangeListener(this)
                        when (v) {
                            skeleton -> itemHeight = bottom - top
                            parent -> parentHeight = bottom - top
                        }
                        calcItemCount()
                    }
                }
            }
            if (itemHeight == 0) {
                skeleton.addOnLayoutChangeListener(listener)
            }
            if (parentHeight == 0) {
                parent.addOnLayoutChangeListener(listener)
            }
            calcItemCount()
        }
        return SkeletonRecyclerViewHolder(skeleton)
    }

    override fun onBindViewHolder(holder: SkeletonRecyclerViewHolder, position: Int) = Unit

    override fun getItemCount(): Int {
        return when (measured) {
            true -> itemCount
            else -> 1
        }
    }
}
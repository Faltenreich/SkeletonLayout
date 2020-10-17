package com.faltenreich.skeletonlayout.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.faltenreich.skeletonlayout.createSkeleton
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection

internal class SkeletonRecyclerViewAdapter(
    @LayoutRes private val layoutResId: Int,
    private val itemCount: Int,
    @ColorInt private val maskColor: Int,
    private val cornerRadius: Float,
    private val showShimmer: Boolean,
    @ColorInt private val shimmerColor: Int,
    private val shimmerDurationInMillis: Long,
    private val shimmerDirection: SkeletonShimmerDirection,
    private val shimmerAngle: Int
) : RecyclerView.Adapter<SkeletonRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonRecyclerViewHolder {
        val originView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        val skeleton = originView.createSkeleton(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle) as SkeletonLayout
        skeleton.layoutParams = originView.layoutParams
        skeleton.showSkeleton()
        return SkeletonRecyclerViewHolder(skeleton)
    }

    override fun onBindViewHolder(holder: SkeletonRecyclerViewHolder, position: Int) = Unit

    override fun getItemCount() = itemCount
}
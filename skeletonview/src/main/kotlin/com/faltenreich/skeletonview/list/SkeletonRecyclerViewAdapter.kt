package com.faltenreich.skeletonview.list

import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.faltenreich.skeletonview.mask.SkeletonMaskLayout

internal class SkeletonRecyclerViewAdapter(
        @LayoutRes private val layoutResId: Int,
        private val itemCount: Int,
        @ColorInt private val maskColor: Int,
        private val cornerRadius: Float,
        private val showShimmer: Boolean,
        @ColorInt private val shimmerColor: Int,
        private val shimmerDurationInMillis: Long,
        private val shimmerAngle: Float
) : RecyclerView.Adapter<SkeletonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonViewHolder {
        val originView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        val maskView = SkeletonMaskLayout(originView, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerAngle)
        return SkeletonViewHolder(maskView)
    }

    override fun onBindViewHolder(holder: SkeletonViewHolder, position: Int) = holder.maskLayout.bind()

    override fun onViewDetachedFromWindow(holder: SkeletonViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.maskLayout.unbind()
    }

    override fun onViewRecycled(holder: SkeletonViewHolder) {
        super.onViewRecycled(holder)
        holder.maskLayout.unbind()
    }

    override fun getItemCount() = itemCount
}
package com.faltenreich.skeletonlayout.recyclerview

import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.faltenreich.skeletonlayout.applySkeleton

internal class SkeletonRecyclerViewAdapter(
        @LayoutRes private val layoutResId: Int,
        private val itemCount: Int,
        @ColorRes private val maskColorResId: Int,
        private val cornerRadius: Float,
        private val showShimmer: Boolean,
        @ColorRes private val shimmerColorResId: Int,
        private val shimmerDurationInMillis: Long
) : RecyclerView.Adapter<SkeletonRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonRecyclerViewHolder {
        val originView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        val skeleton = originView.applySkeleton(maskColorResId, cornerRadius, showShimmer, shimmerColorResId, shimmerDurationInMillis) as SkeletonLayout
        return SkeletonRecyclerViewHolder(skeleton)
    }

    override fun onBindViewHolder(holder: SkeletonRecyclerViewHolder, position: Int) = Unit

    override fun getItemCount() = itemCount
}
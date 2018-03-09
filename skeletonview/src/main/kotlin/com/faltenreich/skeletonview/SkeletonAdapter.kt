package com.faltenreich.skeletonview

import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

internal class SkeletonAdapter(
        @LayoutRes private val layoutResId: Int,
        private val itemCount: Int,
        @ColorInt private val maskColor: Int,
        private val cornerRadius: Float,
        private val showShimmer: Boolean,
        @ColorInt private val shimmerColor: Int
) : RecyclerView.Adapter<SkeletonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonViewHolder {
        val originView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        val maskView = MaskLayout(originView, maskColor, cornerRadius, showShimmer, shimmerColor)
        return SkeletonViewHolder(maskView)
    }

    override fun onBindViewHolder(holder: SkeletonViewHolder, position: Int) = holder.maskLayout.bind()

    override fun getItemCount() = itemCount
}
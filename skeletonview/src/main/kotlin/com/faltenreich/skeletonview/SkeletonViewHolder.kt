package com.faltenreich.skeletonview

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SkeletonViewHolder(
        @LayoutRes layoutResId: Int,
        parent: ViewGroup,
        private val view: View = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
) : RecyclerView.ViewHolder(view) {

    fun bind() {
        scan(view)
    }

    private fun scan(view: View) {
        (view as? ViewGroup)?.let { it.views().forEach { scan(it) } } ?: mask(view)
    }

    private fun mask(view: View) {
        // TODO: Draw within view dimensions
    }
}
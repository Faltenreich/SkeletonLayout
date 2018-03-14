package com.faltenreich.skeletonview.logic

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) = Unit

    override fun getItemCount() = 0
}
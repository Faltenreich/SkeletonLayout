package com.faltenreich.skeletonview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(parent)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = Unit

    override fun getItemCount() = 0
}
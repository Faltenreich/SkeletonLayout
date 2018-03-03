package com.faltenreich.skeletonview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.*

class ListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)), LayoutContainer {

    override val containerView: View? = itemView

    fun bind(listItem: ListItem) {
        imageView.setBackgroundColor(listItem.color)
        titleView.text = listItem.title
        descriptionView.text = listItem.description
    }
}
package com.faltenreich.skeletonlayout.demo.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.demo.R
import com.faltenreich.skeletonlayout.demo.databinding.ListItemRecyclerviewBinding

class RecyclerViewHolder(
    parent: ViewGroup,
    view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_recyclerview, parent, false),
) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemRecyclerviewBinding.bind(view)

    fun bind(listItem: RecyclerViewListItem) = with(binding) {
        wallpaperView.setImageResource(listItem.wallpaperResId)
        avatarView.setImageResource(listItem.avatarResId)
        titleView.setText(listItem.titleResId)
        descriptionView.setText(listItem.descriptionResId)
    }
}
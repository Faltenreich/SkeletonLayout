package com.faltenreich.skeletonlayout.demo.fragment

import android.os.Bundle
import android.view.View
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.demo.R
import com.faltenreich.skeletonlayout.demo.logic.RecyclerViewListItem
import kotlinx.android.synthetic.main.fragment_viewgroup.*
import kotlinx.android.synthetic.main.list_item.*

class ViewGroupFragment : BaseSkeletonFragment(R.layout.fragment_viewgroup, "ViewGroup") {

    override lateinit var skeleton: Skeleton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = RecyclerViewListItem.DEMO.first()
        wallpaperView.setImageResource(item.wallpaperResId)
        avatarView.setImageResource(item.avatarResId)
        titleView.setText(item.titleResId)
        descriptionView.setText(item.descriptionResId)

        skeleton = skeletonLayout
        skeletonLayout.showSkeleton()
    }
}
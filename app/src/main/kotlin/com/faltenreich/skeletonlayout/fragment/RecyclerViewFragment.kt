package com.faltenreich.skeletonlayout.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.BuildConfig
import com.faltenreich.skeletonlayout.R
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.logic.RecyclerViewAdapter
import com.faltenreich.skeletonlayout.logic.RecyclerViewListItem
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class RecyclerViewFragment : BaseSkeletonFragment(R.layout.fragment_recyclerview, "RecyclerView") {

    override lateinit var skeleton: Skeleton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = RecyclerViewListItem.DEMO

        val listAdapter = RecyclerViewAdapter(items)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listAdapter

        val skeletonItemSize = if (BuildConfig.isDemoMode) items.size else SKELETON_ITEM_COUNT
        skeleton = list.applySkeleton(R.layout.list_item, skeletonItemSize).apply { showSkeleton() }
    }

    companion object {
        private const val SKELETON_ITEM_COUNT = 30
    }
}
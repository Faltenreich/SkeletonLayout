package com.faltenreich.skeletonlayout.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.faltenreich.skeletonlayout.R
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeletonAdapter
import com.faltenreich.skeletonlayout.logic.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class RecyclerViewFragment : BaseSkeletonFragment(R.layout.fragment_recyclerview, "RecyclerView") {

    private lateinit var skeleton: Skeleton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = RecyclerViewAdapter()
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listAdapter

        skeleton = list.applySkeletonAdapter(R.layout.list_item, itemCount = RecyclerViewAdapter.ITEM_COUNT).apply { showSkeleton() }
    }

    override fun getSkeleton(): Skeleton = skeleton
}
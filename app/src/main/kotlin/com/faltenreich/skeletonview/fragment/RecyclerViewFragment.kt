package com.faltenreich.skeletonview.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.faltenreich.skeletonview.R
import com.faltenreich.skeletonview.SkeletonView
import com.faltenreich.skeletonview.logic.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class RecyclerViewFragment : BaseFragment(R.layout.fragment_recyclerview, "RecyclerView") {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = RecyclerViewAdapter()
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listAdapter

        val skeletonView = SkeletonView(list, R.layout.list_item, itemCount = 30)
        skeletonView.show()
    }
}
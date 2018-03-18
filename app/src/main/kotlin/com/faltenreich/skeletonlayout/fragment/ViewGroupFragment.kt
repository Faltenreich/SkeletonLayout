package com.faltenreich.skeletonlayout.fragment

import android.os.Bundle
import android.view.View
import com.faltenreich.skeletonlayout.R
import com.faltenreich.skeletonlayout.Skeleton
import kotlinx.android.synthetic.main.fragment_viewgroup.*

class ViewGroupFragment : BaseSkeletonFragment(R.layout.fragment_viewgroup, "ViewGroup") {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        skeletonLayout.showSkeleton()
    }

    override fun getSkeleton(): Skeleton = skeletonLayout
}
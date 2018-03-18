package com.faltenreich.skeletonlayout.fragment

import com.faltenreich.skeletonlayout.R
import com.faltenreich.skeletonlayout.SkeletonLayout
import kotlinx.android.synthetic.main.fragment_viewgroup.*

class ViewGroupFragment : BaseSkeletonFragment(R.layout.fragment_viewgroup, "ViewGroup") {

    override fun getSkeleton(): SkeletonLayout = skeletonLayout
}
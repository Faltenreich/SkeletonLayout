package com.faltenreich.skeletonlayout.fragment

import android.support.annotation.LayoutRes
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.logic.ValueChangedListener

abstract class BaseSkeletonFragment(@LayoutRes layoutResId: Int, title: String) : BaseFragment(layoutResId, title), ValueChangedListener {

    override fun onMaskColorChanged(value: Int) {
        getSkeleton().maskColor = value
    }

    override fun onMaskCornerRadiusChanged(value: Float) {
        getSkeleton().maskCornerRadius = value
    }

    override fun onShowShimmerChanged(value: Boolean) {
        getSkeleton().showShimmer = value
    }

    override fun onShimmerColorChanged(value: Int) {
        getSkeleton().shimmerColor = value
    }

    override fun onShimmerDurationChanged(value: Long) {
        getSkeleton().shimmerDurationInMillis = value
    }

    abstract fun getSkeleton(): Skeleton
}
package com.faltenreich.skeletonlayout.fragment

import android.support.annotation.LayoutRes
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.logic.ValueChangedListener

abstract class BaseSkeletonFragment(@LayoutRes layoutResId: Int, title: String) : BaseFragment(layoutResId, title), ValueChangedListener {

    abstract var skeleton: Skeleton

    override fun onMaskColorChanged(value: Int) {
        skeleton.maskColor = value
    }

    override fun onMaskCornerRadiusChanged(value: Float) {
        skeleton.maskCornerRadius = value
    }

    override fun onShowShimmerChanged(value: Boolean) {
        skeleton.showShimmer = value
    }

    override fun onShimmerColorChanged(value: Int) {
        skeleton.shimmerColor = value
    }

    override fun onShimmerDurationChanged(value: Long) {
        skeleton.shimmerDurationInMillis = value
    }
}
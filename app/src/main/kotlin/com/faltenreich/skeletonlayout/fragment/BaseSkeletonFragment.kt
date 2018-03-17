package com.faltenreich.skeletonlayout.fragment

import android.support.annotation.LayoutRes
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.faltenreich.skeletonlayout.logic.ValueChangedListener

abstract class BaseSkeletonFragment(@LayoutRes layoutResId: Int, title: String) : BaseFragment(layoutResId, title), ValueChangedListener {

    override fun onMaskColorChanged(value: Int) {
        getSkeletonLayout().maskColor = value
    }

    override fun onMaskCornerRadiusChanged(value: Float) {
        getSkeletonLayout().cornerRadius = value
    }

    override fun onShowShimmerChanged(value: Boolean) {
        getSkeletonLayout().showShimmer = value
    }

    override fun onShimmerColorChanged(value: Int) {
        getSkeletonLayout().shimmerColor = value
    }

    override fun onShimmerDurationChanged(value: Long) {
        getSkeletonLayout().shimmerDurationInMillis = value
    }

    abstract fun getSkeletonLayout(): SkeletonLayout
}
package com.faltenreich.skeletonview.mask

import android.support.annotation.ColorInt
import android.view.View

internal class SkeletonMaskSolid(view: View, @ColorInt color: Int) : SkeletonMask(view, color) {
    override fun invalidate() = Unit
    override fun start() = Unit
    override fun stop() = Unit
}
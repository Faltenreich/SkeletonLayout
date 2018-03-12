package com.faltenreich.skeletonview.mask

internal interface SkeletonMaskable {
    fun invalidate()
    fun start()
    fun stop()
}
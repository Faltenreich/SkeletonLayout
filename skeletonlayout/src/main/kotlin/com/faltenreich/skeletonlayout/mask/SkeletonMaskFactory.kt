package com.faltenreich.skeletonlayout.mask

import android.view.View
import com.faltenreich.skeletonlayout.SkeletonConfig

internal object SkeletonMaskFactory {

    fun createMask(
        view: View,
        config: SkeletonConfig
    ): SkeletonMask {
        return when {
            config.showPulse -> SkeletonMaskPulse(
                view,
                config.maskColor,
                config.pulseDurationInMillis,
                config.pulseMinAlpha,
                config.pulseMaxAlpha
            )
            config.showShimmer -> SkeletonMaskShimmer(
                view,
                config.maskColor,
                config.shimmerColor,
                config.shimmerDurationInMillis,
                config.shimmerDirection,
                config.shimmerAngle
            )
            else -> SkeletonMaskSolid(view, config.maskColor)
        }
    }
}
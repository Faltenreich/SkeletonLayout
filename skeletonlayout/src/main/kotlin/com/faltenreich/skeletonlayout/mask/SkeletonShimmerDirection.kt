package com.faltenreich.skeletonlayout.mask

enum class SkeletonShimmerDirection(private val stableId: Int) {
    LEFT_TO_RIGHT(0),
    RIGHT_TO_LEFT(1);

    companion object {

        fun valueOf(stableId: Int): SkeletonShimmerDirection? {
            return values().firstOrNull { it.stableId == stableId }
        }
    }
}
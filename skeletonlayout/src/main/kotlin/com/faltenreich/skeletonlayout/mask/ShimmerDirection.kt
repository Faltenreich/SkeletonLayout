package com.faltenreich.skeletonlayout.mask

enum class ShimmerDirection(private val stableId: Int) {
    LEFT_TO_RIGHT(0),
    RIGHT_TO_LEFT(1);

    companion object {

        fun valueOf(stableId: Int): ShimmerDirection? {
            return values().firstOrNull { it.stableId == stableId }
        }
    }
}
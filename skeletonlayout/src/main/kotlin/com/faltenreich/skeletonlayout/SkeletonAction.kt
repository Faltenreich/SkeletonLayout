package com.faltenreich.skeletonlayout

interface SkeletonAction {

    /**
     * Display the original layout and hide the skeleton
     */
    fun showOriginal()

    /**
     * Display a skeleton and hide the original layout
     */
    fun showSkeleton()

    /**
     * @return True if the original layout is hidden by the skeleton
     */
    fun isSkeleton(): Boolean
}
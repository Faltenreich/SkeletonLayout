package com.faltenreich.skeletonlayout

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SkeletonKotlinInstrumentedTest {

    @Test
    fun applySkeleton() {
        val recyclerView = RecyclerView(InstrumentationRegistry.getTargetContext())
        val skeleton = SkeletonFactory.skeletonForView(recyclerView, R.layout.list_item)
        Assert.assertEquals(skeleton.isSkeleton(), false)
        skeleton.showSkeleton()
        Assert.assertEquals(skeleton.isSkeleton(), true)
        skeleton.showOriginal()
        Assert.assertEquals(skeleton.isSkeleton(), false)
    }
}

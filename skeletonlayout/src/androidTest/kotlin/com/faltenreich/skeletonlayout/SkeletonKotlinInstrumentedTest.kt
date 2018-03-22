package com.faltenreich.skeletonlayout

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.View
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SkeletonKotlinInstrumentedTest {

    @Test
    fun testSkeleton() {
        val view = View(InstrumentationRegistry.getTargetContext())
        val skeleton = SkeletonFactory.skeletonForView(view)
        Assert.assertEquals(skeleton.isSkeleton(), false)
        skeleton.showSkeleton()
        Assert.assertEquals(skeleton.isSkeleton(), true)
        skeleton.showOriginal()
        Assert.assertEquals(skeleton.isSkeleton(), false)
    }
}

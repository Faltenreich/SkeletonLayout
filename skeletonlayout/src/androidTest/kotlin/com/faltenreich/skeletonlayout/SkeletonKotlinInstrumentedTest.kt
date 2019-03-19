package com.faltenreich.skeletonlayout

import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SkeletonKotlinInstrumentedTest {

    @Test
    fun testSkeleton() {
        val view = View(InstrumentationRegistry.getInstrumentation().context)
        val skeleton = view.createSkeleton()
        Assert.assertEquals(skeleton.isSkeleton(), false)
        skeleton.showSkeleton()
        Assert.assertEquals(skeleton.isSkeleton(), true)
        skeleton.showOriginal()
        Assert.assertEquals(skeleton.isSkeleton(), false)
    }
}

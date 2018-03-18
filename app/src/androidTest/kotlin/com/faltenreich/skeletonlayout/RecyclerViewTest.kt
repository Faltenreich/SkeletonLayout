package com.faltenreich.skeletonlayout

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    @Test
    fun useAppContext() {
        val recyclerView = RecyclerView(InstrumentationRegistry.getTargetContext())
        recyclerView.applySkeletonAdapter(R.layout.list_item)
    }
}

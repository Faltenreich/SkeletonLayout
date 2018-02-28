package com.faltenreich.skeletonview

import android.view.View
import android.view.ViewGroup

internal fun Any.tag(): String = javaClass.simpleName

internal fun ViewGroup.views(): List<View> = (0 until childCount).map { getChildAt(it) }
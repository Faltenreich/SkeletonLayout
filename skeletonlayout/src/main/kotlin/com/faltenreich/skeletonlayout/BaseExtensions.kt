package com.faltenreich.skeletonlayout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat

internal fun Any.tag(): String = javaClass.simpleName

internal fun Context.refreshRateInSeconds(): Float {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    return windowManager?.defaultDisplay?.refreshRate ?: 60f
}

internal fun ViewGroup.views(): List<View> {
    return (0 until childCount).map { child -> getChildAt(child) }
}

internal fun View.isAttachedToWindowCompat(): Boolean {
    return ViewCompat.isAttachedToWindow(this)
}
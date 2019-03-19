package com.faltenreich.skeletonlayout

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat

internal fun Any.tag(): String = javaClass.simpleName

internal fun Context.refreshRateInSeconds(): Float = (getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.refreshRate ?: 60f

internal fun ViewGroup.views(): List<View> = (0 until childCount).map { getChildAt(it) }

internal fun View.isAttachedToWindowCompat() = ViewCompat.isAttachedToWindow(this)
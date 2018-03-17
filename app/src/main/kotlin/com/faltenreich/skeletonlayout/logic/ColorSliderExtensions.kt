package com.faltenreich.skeletonlayout.logic

import android.content.Context
import android.graphics.Color
import android.util.Log

internal fun Context.defaultColors(): IntArray {
    val attrs = obtainStyledAttributes(com.github.naz013.colorslider.R.styleable.ColorSlider)
    return try {
        // FIXME
        val id = attrs.getResourceId(com.github.naz013.colorslider.R.styleable.ColorSlider_cs_colors, 0)
        val ids = resources.getIntArray(id)
        val colors = IntArray(ids.size)
        System.arraycopy(ids, 0, colors, 0, ids.size)
        colors
    } catch (exception: Exception) {
        Log.e(javaClass.simpleName, exception.message)
        intArrayOf(Color.BLACK, Color.DKGRAY, Color.GRAY, Color.WHITE, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA)
    } finally {
        attrs.recycle()
    }
}
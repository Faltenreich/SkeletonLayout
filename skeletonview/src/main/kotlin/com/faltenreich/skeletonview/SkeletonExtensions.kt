package com.faltenreich.skeletonview

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import java.util.*

internal fun Any.tag(): String = javaClass.simpleName

internal fun Context.refreshRateInSeconds(): Float = (getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.refreshRate ?: 60f

internal fun ViewGroup.views(): List<View> = (0 until childCount).map { getChildAt(it) }

internal fun View.setOnLayoutChangeListener(action: () -> Unit) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(view: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            removeOnLayoutChangeListener(this)
            action()
        }
    })
}

internal fun View.isAttachedToWindowCompat() = ViewCompat.isAttachedToWindow(this)

internal fun Int.brighten(factor: Float): Int {
    val a = Color.alpha(this)
    val r = Math.round(Color.red(this) * factor)
    val g = Math.round(Color.green(this) * factor)
    val b = Math.round(Color.blue(this) * factor)
    return Color.argb(a, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255))
}

internal fun Int.alpha(factor: Float): Int {
    val alpha = (Color.alpha(this) * factor).toInt()
    return Color.argb(alpha, Color.red(this), Color.green(this), Color.blue(this))
}

internal fun Calendar.withTimeAtStartOfDay() = apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}
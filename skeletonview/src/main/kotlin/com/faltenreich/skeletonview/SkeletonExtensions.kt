package com.faltenreich.skeletonview

import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup

internal fun Any.tag(): String = javaClass.simpleName

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
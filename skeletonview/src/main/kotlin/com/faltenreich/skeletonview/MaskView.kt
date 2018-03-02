package com.faltenreich.skeletonview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

internal class MaskView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        originView: View? = null,
        @ColorInt maskColor: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    constructor(originalView: View, @ColorInt maskColor: Int) : this(originalView.context, null, 0, originalView, maskColor)

    private val paint: Paint = Paint()
    private val maskAreas = ArrayList<Rect>()

    init {
        paint.apply {
            color = maskColor
            style = Paint.Style.FILL
        }

        originView?.let {
            layoutParams = it.layoutParams
            it.visibility = View.INVISIBLE

            addView(it)
            setWillNotDraw(false)

        } ?: Log.e(tag(), "Missing view to mask")
    }

    fun mask() {
        setOnLayoutChangeListener { maskViews(this, this) }
    }

    private fun maskViews(view: View, root: ViewGroup) {
        (view as? ViewGroup)?.let { it.views().forEach { maskViews(it, root) } } ?: maskView(view, root)
    }

    private fun maskView(view: View, root: ViewGroup) {
        val rect = Rect()
        view.getDrawingRect(rect)
        root.offsetDescendantRectToMyCoords(view, rect)
        maskAreas.add(rect)
    }

    override fun onDraw(canvas: Canvas) {
        Log.d(tag(), maskAreas.toString())
        maskAreas.forEach { canvas.drawRect(it, paint) }
    }
}
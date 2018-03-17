package com.faltenreich.skeletonlayout.mask

import android.graphics.*
import android.support.annotation.ColorInt
import android.view.View

internal abstract class SkeletonMask(protected val parent: View, @ColorInt protected val color: Int) : SkeletonMaskable {

    private val bitmap: Bitmap by lazy { createBitmap() }
    private val canvas: Canvas by lazy { createCanvas() }
    protected val paint: Paint by lazy { createPaint() }

    private fun createBitmap(): Bitmap = Bitmap.createBitmap(parent.width, parent.height, Bitmap.Config.ALPHA_8)
    private fun createCanvas(): Canvas = Canvas(bitmap)
    protected open fun createPaint(): Paint = Paint().also { it.color = color }

    fun draw(rectF: RectF, cornerRadius: Float, paint: Paint) {
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
    }

    fun draw(rect: Rect, paint: Paint) {
        canvas.drawRect(rect, paint)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}
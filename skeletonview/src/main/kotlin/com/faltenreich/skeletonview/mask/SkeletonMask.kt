package com.faltenreich.skeletonview.mask

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.view.View

abstract class SkeletonMask(protected val parent: View, @ColorInt protected val color: Int) : SkeletonMaskable {

    val bitmap: Bitmap by lazy { createBitmap() }
    val canvas: Canvas by lazy { createCanvas() }
    val paint: Paint by lazy { createPaint() }

    protected open fun createBitmap(): Bitmap = Bitmap.createBitmap(parent.width, parent.height, Bitmap.Config.ALPHA_8)
    protected open fun createCanvas(): Canvas = Canvas(bitmap)
    protected open fun createPaint(): Paint = Paint().also { it.color = color }
}
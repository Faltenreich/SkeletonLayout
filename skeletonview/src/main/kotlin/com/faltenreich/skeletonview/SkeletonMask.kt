package com.faltenreich.skeletonview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.view.View

class SkeletonMask(view: View, @ColorInt private val color: Int) {
    val bitmap: Bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ALPHA_8)
    val canvas: Canvas = Canvas(bitmap)
    val paint: Paint = Paint().also { it.color = color }
}
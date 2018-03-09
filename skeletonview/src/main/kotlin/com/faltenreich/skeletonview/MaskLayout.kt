package com.faltenreich.skeletonview

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

internal class MaskLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        originView: View? = null,
        @ColorInt private val maskColor: Int = 0,
        private val cornerRadius: Float = 0f,
        @ColorInt private val shimmerColor: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    constructor(
            originalView: View,
            @ColorInt maskColor: Int,
            cornerRadius: Float,
            @ColorInt shimmerColor: Int
    ) : this(originalView.context, null, 0, originalView, maskColor, cornerRadius, shimmerColor)

    private lateinit var bitmap: Bitmap
    private lateinit var paint: Paint

    private lateinit var shimmerBitmap: Bitmap
    private lateinit var shimmerPaint: Paint

    init {
        originView?.let {
            layoutParams = it.layoutParams
            it.visibility = View.INVISIBLE
            addView(it)
            setWillNotDraw(false)
        } ?: Log.e(tag(), "Missing view to mask")
    }

    fun bind() {
        setOnLayoutChangeListener { mask() }
    }

    private fun test() {
        shimmerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)

        val shimmerColors: IntArray = intArrayOf(Color.TRANSPARENT, shimmerColor, Color.TRANSPARENT)
        val shimmerPositions: FloatArray = floatArrayOf(0f, .5f, 1f)
        val shimmerWidth = 30f
        shimmerPaint.apply {
            isAntiAlias = true
            shader = LinearGradient(0f, 0f, shimmerWidth, 0f, shimmerColors, shimmerPositions, Shader.TileMode.CLAMP)
        }

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    private fun mask() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)

        val canvas = Canvas(bitmap)

        paint = Paint().apply {
            color = maskColor
            style = Paint.Style.FILL
        }

        val xferPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
            isAntiAlias = true
            style = Paint.Style.FILL
        }

        maskViews(canvas, xferPaint, this, this)
    }

    private fun maskViews(canvas: Canvas, paint: Paint, view: View, root: ViewGroup) {
        (view as? ViewGroup)?.let { it.views().forEach { maskViews(canvas, paint, it, root) } } ?: maskView(canvas, paint, view, root)
    }

    private fun maskView(canvas: Canvas, paint: Paint, view: View, root: ViewGroup) {
        val rect = Rect()
        view.getDrawingRect(rect)
        root.offsetDescendantRectToMyCoords(view, rect)

        if (cornerRadius > 0) {
            val rectF = RectF(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat())
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
        } else {
            canvas.drawRect(rect, paint)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}
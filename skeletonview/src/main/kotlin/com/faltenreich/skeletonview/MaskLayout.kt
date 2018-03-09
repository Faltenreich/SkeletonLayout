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
        private val showShimmer: Boolean = true,
        @ColorInt private val shimmerColor: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    constructor(
            originalView: View,
            @ColorInt maskColor: Int,
            cornerRadius: Float,
            showShimmer: Boolean,
            @ColorInt shimmerColor: Int
    ) : this(originalView.context, null, 0, originalView, maskColor, cornerRadius, showShimmer, shimmerColor)

    private lateinit var maskBitmap: Bitmap
    private lateinit var maskPaint: Paint

    private var shimmerBitmap: Bitmap? = null
    private var shimmerPaint: Paint? = null

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

    private fun mask() {
        maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)

        val canvas = Canvas(maskBitmap)

        maskPaint = Paint().apply {
            color = maskColor
        }

        if (showShimmer) {
            shimmerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
            val shimmerColors: IntArray = intArrayOf(Color.TRANSPARENT, shimmerColor, Color.TRANSPARENT)
            val shimmerPositions: FloatArray = floatArrayOf(0f, .5f, 1f)
            shimmerPaint = Paint().apply {
                isAntiAlias = true
                shader = LinearGradient(0f, 0f, width.toFloat(), 0f, shimmerColors, shimmerPositions, Shader.TileMode.CLAMP)
            }
        }

        val xferPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
            isAntiAlias = cornerRadius > 0
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
        canvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint)
    }
}
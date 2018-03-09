package com.faltenreich.skeletonview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import java.util.*

internal class MaskLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        originView: View? = null,
        @ColorInt private val maskColor: Int = 0,
        private val cornerRadius: Float = 0f,
        private val showShimmer: Boolean = true,
        @ColorInt private val shimmerColor: Int = 0,
        private val shimmerDurationInMillis: Long = 0,
        private val shimmerAngle: Float = 0f
) : FrameLayout(context, attrs, defStyleAttr) {

    constructor(
            originalView: View,
            @ColorInt maskColor: Int,
            cornerRadius: Float,
            showShimmer: Boolean,
            @ColorInt shimmerColor: Int,
            shimmerDuration: Long = 0,
            shimmerAngle: Float = 0f
    ) : this(originalView.context, null, 0, originalView, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDuration, shimmerAngle)

    private lateinit var maskBitmap: Bitmap
    private lateinit var maskPaint: Paint

    private var shimmerBitmap: Bitmap? = null
    private var shimmerPaint: Paint? = null

    private val shimmerWidth: Float
        get() = width.toFloat()

    private val shimmerAnimation by lazy {
        ValueAnimator.ofFloat(-1f, 1f).apply {
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener { if (isAttachedToWindowCompat()) updateShimmer() else cancel() }
        }
    }

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

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        invalidateShimmer()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint)
        shimmerBitmap?.let { canvas.drawBitmap(it, 0f, 0f, shimmerPaint) }
    }

    private fun mask() {
        maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)

        val canvas = Canvas(maskBitmap)

        maskPaint = Paint().apply {
            color = maskColor
        }

        val xferPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
            isAntiAlias = cornerRadius > 0
        }

        maskViews(canvas, xferPaint, this, this)

        if (showShimmer) {
            shimmerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
            val shimmerCanvas = Canvas(shimmerBitmap)
            shimmerPaint = Paint().apply {
                shader = LinearGradient(0f, 0f, shimmerWidth, shimmerAngle, Color.argb(255, 255, 255, 255), shimmerColor, Shader.TileMode.MIRROR)
                isAntiAlias = true
            }

            // TODO: Improve double loop
            maskViews(shimmerCanvas, xferPaint, this, this)
        }
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

    private fun invalidateShimmer() {
        when (visibility) {
            View.VISIBLE -> shimmerAnimation.start()
            else -> shimmerAnimation.cancel()
        }
    }

    // Offset is time-dependent to support synchronization between uncoupled views
    private fun shimmerOffset(): Float {
        val now = Calendar.getInstance()
        val millis = (now.timeInMillis - now.withTimeAtStartOfDay().timeInMillis).toFloat()
        val interval = shimmerDurationInMillis
        val divisor = millis / interval
        val divisorAbs = Math.round(divisor)
        val start = divisorAbs * interval
        val end = start + interval
        val percentage = (millis - start) / (end - start)
        return shimmerWidth * percentage
    }

    private fun updateShimmer() {
        shimmerPaint?.let {
            val offset = shimmerOffset()
            it.shader = LinearGradient(offset, 0f, offset + shimmerWidth, shimmerAngle, Color.TRANSPARENT, shimmerColor, Shader.TileMode.MIRROR)
            invalidate()
        }
    }
}
package com.faltenreich.skeletonview

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
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
        get() = width.toFloat() / 2

    private val shimmerRefreshInterval by lazy { (1000f / context.refreshRateInSeconds()).toInt() }

    private var shimmerAnimation: Job? = null

    init {
        originView?.let {
            layoutParams = it.layoutParams
            it.visibility = View.INVISIBLE
            addView(it)
            setWillNotDraw(false)
        } ?: Log.e(tag(), "Missing view to mask")
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        invalidateShimmer()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint)
        shimmerBitmap?.let { canvas.drawBitmap(it, 0f, 0f, shimmerPaint) }
    }

    fun bind() {
        setOnLayoutChangeListener { mask() }
    }

    fun unbind() {
        stopShimmer()
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
            View.VISIBLE -> startShimmer()
            else -> stopShimmer()
        }
    }

    // Offset is time-dependent to support synchronization between uncoupled views
    private fun shimmerOffset(): Float {
        val now = Calendar.getInstance()
        val millis = (now.timeInMillis - now.withTimeAtStartOfDay().timeInMillis)

        val current = millis.toDouble()
        val interval = shimmerDurationInMillis
        val divisor = Math.floor(current / interval)
        val start = interval * divisor
        val end = start + interval
        val percentage = (current - start) / (end - start)

        Log.d(tag(), "Updating shimmer: $percentage")

        return (shimmerWidth * percentage).toFloat()
    }

    private fun updateShimmer() {
        shimmerPaint?.let {
            val offset = shimmerOffset()
            it.shader = LinearGradient(offset, 0f, offset + shimmerWidth, shimmerAngle, shimmerColor, Color.TRANSPARENT, Shader.TileMode.REPEAT)
            invalidate()
        }
    }

    private fun startShimmer() {
        shimmerAnimation = launch(UI) {
            while (isActive) {
                updateShimmer()
                delay(shimmerRefreshInterval)
            }
        }
    }

    private fun stopShimmer() {
        shimmerAnimation?.cancel()
        shimmerAnimation = null
    }
}
package com.faltenreich.skeletonview.mask

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.faltenreich.skeletonview.setOnLayoutChangeListener
import com.faltenreich.skeletonview.tag
import com.faltenreich.skeletonview.views

internal class SkeletonMaskLayout @JvmOverloads constructor(
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

    private var mask: SkeletonMask? = null

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
        mask?.invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        mask?.let { canvas.drawBitmap(it.bitmap, 0f, 0f, it.paint) }

    }

    fun bind() {
        setOnLayoutChangeListener {
            mask()
            mask?.invalidate()
        }
    }

    fun unbind() {
        mask?.stop()
    }

    private fun mask() {
        mask = if (showShimmer) SkeletonMaskShimmer(this, maskColor, shimmerColor, shimmerDurationInMillis, shimmerAngle) else SkeletonMaskSolid(this, maskColor)
        mask?.let {
            val xferPaint = Paint().apply {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
                isAntiAlias = cornerRadius > 0
            }
            maskViews(it.canvas, xferPaint, this, this)
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
}
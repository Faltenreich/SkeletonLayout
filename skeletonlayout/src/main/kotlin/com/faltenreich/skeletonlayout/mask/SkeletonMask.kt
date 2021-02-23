package com.faltenreich.skeletonlayout.mask

import android.graphics.*
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.faltenreich.skeletonlayout.tag
import com.faltenreich.skeletonlayout.views

internal abstract class SkeletonMask(protected val parent: View, @ColorInt color: Int) : SkeletonMaskable {

    var color: Int = color
        set(value) {
            paint.color = value
            field = value
        }

    private val bitmap: Bitmap by lazy { createBitmap() }
    private val canvas: Canvas by lazy { createCanvas() }
    protected val paint: Paint by lazy { createPaint() }

    protected open fun createBitmap(): Bitmap = Bitmap.createBitmap(parent.width, parent.height, Bitmap.Config.ALPHA_8)

    protected open fun createCanvas(): Canvas = Canvas(bitmap)

    protected open fun createPaint(): Paint = Paint().also { it.color = color }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }

    private fun draw(rectF: RectF, cornerRadius: Float, paint: Paint) {
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
    }

    private fun draw(rect: Rect, paint: Paint) {
        canvas.drawRect(rect, paint)
    }

    fun mask(viewGroup: ViewGroup, maskCornerRadius: Float, offsetFromRootView:Int) {
        val xferPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
            isAntiAlias = maskCornerRadius > 0
        }
        mask(viewGroup, viewGroup, xferPaint, maskCornerRadius,offsetFromRootView,currentLevel=0)
    }

    private fun mask(view: View, root: ViewGroup, paint: Paint, maskCornerRadius: Float, limitViewLevel: Int, currentLevel:Int = 0) {
        (view as? ViewGroup)?.let { viewGroup ->
            viewGroup.views().forEach {
                if (limitViewLevel <= currentLevel){
                    maskView(it, root, paint, maskCornerRadius)
                }
                else{
                    mask(it, root, paint, maskCornerRadius,limitViewLevel,currentLevel+1)
                }

            }
        } ?: maskView(view, root, paint, maskCornerRadius)
    }

    private fun maskView(view: View, root: ViewGroup, paint: Paint, maskCornerRadius: Float) {
        validate(view)

        val rect = Rect()
        view.getDrawingRect(rect)
        root.offsetDescendantRectToMyCoords(view, rect)

        if (maskCornerRadius > 0) {
            val rectF = RectF(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat())
            draw(rectF, maskCornerRadius, paint)
        } else {
            draw(rect, paint)
        }
    }

    private fun validate(view: View) {
        when (view) {
            is RecyclerView, is ViewPager2 -> Log.w(tag(), "Passing ViewGroup with reusable children to SkeletonLayout - consider using applySkeleton() instead")
        }
    }
}
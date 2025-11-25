package com.pbgames.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class WinningLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFFF5252.toInt()  // Red
        strokeWidth = 14f
        style = Paint.Style.STROKE
    }

    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f

    // For animation
    private var animatedProgress = 0f // 0 → 1
    private var fadeAlpha = 0f       // 0 → 255

    fun drawLineBetween(sx: Float, sy: Float, ex: Float, ey: Float) {
        startX = sx
        startY = sy
        endX = ex
        endY = ey

        visibility = VISIBLE
        animatedProgress = 0f
        fadeAlpha = 0f

        startShowAnimation()
    }

    private fun startShowAnimation() {

        // 1️⃣ Fade in animation
        ValueAnimator.ofFloat(0f, 255f).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                fadeAlpha = it.animatedValue as Float
                invalidate()
            }
            start()
        }

        // 2️⃣ Stretch using spring animation
        SpringAnimation(this, SpringAnimation.SCALE_X, 1f).apply {
            spring = SpringForce(1f).apply {
                dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                stiffness = SpringForce.STIFFNESS_LOW
            }
            setStartValue(0.1f)
            start()
        }

        SpringAnimation(this, SpringAnimation.SCALE_Y, 1f).apply {
            spring = SpringForce(1f).apply {
                dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                stiffness = SpringForce.STIFFNESS_LOW
            }
            setStartValue(0.1f)
            start()
        }

        // 3️⃣ Smooth line draw
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                animatedProgress = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = fadeAlpha.toInt()

        val currentX = startX + (endX - startX) * animatedProgress
        val currentY = startY + (endY - startY) * animatedProgress

        canvas.drawLine(startX, startY, currentX, currentY, paint)
    }

    fun reset() {
        animatedProgress = 0f
        fadeAlpha = 0f
        visibility = GONE
        invalidate()
    }
}

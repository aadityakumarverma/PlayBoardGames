package com.pbgames.utils

import android.animation.ValueAnimator
import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

object AnimationUtils {


    fun View.springScaleInPlace() {
        listOf(SpringAnimation.SCALE_X, SpringAnimation.SCALE_Y).forEach { property ->
            SpringAnimation(this, property, 1f).apply {
                spring = SpringForce(1f).apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                    stiffness = SpringForce.STIFFNESS_LOW
                }
                setStartValue(0f) // start invisible (shrunk)
                start()
            }
        }
    }


    fun View.springScale() {
        // Scale spring animation (X & Y)
        listOf(SpringAnimation.SCALE_X, SpringAnimation.SCALE_Y).forEach { property ->
            SpringAnimation(this, property, 1f).apply {
                spring = SpringForce(1f).apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                    stiffness = SpringForce.STIFFNESS_LOW
                }
                setStartValue(0f) // start from shrunk
                start()
            }
        }

        // Alpha animation (fade in)
        this.alpha = 0f
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 500L // fade duration
            addUpdateListener { animator ->
                this@springScale.alpha = animator.animatedValue as Float
            }
            start()
        }
    }
}
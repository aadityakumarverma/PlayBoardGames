package com.pbgames.utils

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

object AnimationUtils {


    fun View.springRiseAndScale() {
        SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0f).apply {
            spring = SpringForce(0f).apply {
                dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                stiffness = SpringForce.STIFFNESS_LOW
            }
            setStartValue(300f) // starts below view
            start()
        }

        listOf("scaleX", "scaleY").forEach { property ->
            SpringAnimation(this, when (property) {
                "scaleX" -> SpringAnimation.SCALE_X
                else -> SpringAnimation.SCALE_Y
            }, 1f).apply {
                spring = SpringForce(1f).apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                    stiffness = SpringForce.STIFFNESS_LOW
                }
                setStartValue(0f)
                start()
            }
        }
    }


}
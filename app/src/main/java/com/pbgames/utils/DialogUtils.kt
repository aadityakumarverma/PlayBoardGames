package com.pbgames.utils

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.pbgames.databinding.LayoutAirplaneModeDialogBinding

object DialogUtils {



    @Composable
    fun BouncyOverlay(
        onDismiss: () -> Unit,
        content: @Composable () -> Unit
    ) {
        var visible by remember { mutableStateOf(false) }

        // trigger animation
        LaunchedEffect(Unit) { visible = true }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000))
                .padding(20.sdp), // optional dim background
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = scaleIn(
                    initialScale = 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                exit = scaleOut(
                    targetScale = 0f,
                    animationSpec = tween(300)
                )
            ) {
                Surface(
                    shape = RoundedCornerShape(16.sdp),
                    color = Color.White,
                    modifier = Modifier.wrapContentSize()
                ) {
                    content()
                }
            }
        }
    }



    fun showAirplaneModeDialog(
        context: Context,
        layoutInflater: LayoutInflater,
        callback: (res: String) -> Unit
    ) {
        var returnValue = "none"
        val builder = AlertDialog.Builder(context)
        val dialogBinding = LayoutAirplaneModeDialogBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.show()

        // 👉 Apply animation effect here
        dialog.applySpringRiseAndScaleEffect()

        dialogBinding.apply {

            tvGoToSettingBtn.setOnClickListener {
                returnValue = "setting"
                dialog.dismiss()
            }

            ivCloseBtn.setOnClickListener {
                returnValue = "none"
                dialog.dismiss()
            }

            dialog.setOnDismissListener {
                callback(returnValue)
            }
        }
    }


    fun Dialog.applySpringRiseAndScaleEffect() {
        window?.decorView?.findViewById<View>(R.id.content)?.let { rootView ->
            // Reset state
            rootView.translationY = 300f
            rootView.scaleX = 0f
            rootView.scaleY = 0f
            rootView.alpha = 0f

            // 1. Rise (Y)
            SpringAnimation(rootView, SpringAnimation.TRANSLATION_Y, 0f).apply {
                spring = SpringForce(0f).apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                    stiffness = SpringForce.STIFFNESS_LOW
                }
                setStartValue(300f)
                start()
            }

            // 2. Scale (X & Y)
            listOf(SpringAnimation.SCALE_X, SpringAnimation.SCALE_Y).forEach { property ->
                SpringAnimation(rootView, property, 1f).apply {
                    spring = SpringForce(1f).apply {
                        dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                        stiffness = SpringForce.STIFFNESS_LOW
                    }
                    setStartValue(0f)
                    start()
                }
            }

            // 3. Fade in
            rootView.animate().alpha(1f).setDuration(300L).start()
        }
    }

}

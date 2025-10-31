package com.pbgames.utils

import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.navigation.NavOptions
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.pbgames.R

object UtilsFunctions {
    private val animatedPositions = mutableSetOf<Int>()


    val navOptionsPopUpIn = NavOptions.Builder()
        .setPopUpTo(R.id.parent_nav_graph, inclusive = true)
        .build()



    fun morphToProgress(textView: TextView, progressBar: LinearProgressIndicator) {
        textView.text = "Loading..."
        progressBar.visibility = View.VISIBLE
        animateProgress(progressBar)
    }

    fun animateProgress(progressBar : LinearProgressIndicator) {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 1500
        animator.addUpdateListener {
            val progress = it.animatedValue as Int
            progressBar.progress = progress
        }
        animator.start()
    }


    fun showLog(message:String,tag: String = "MyLogTag") {
        Log.d(tag, message)
    }
    /*fun showToast(context: Context, message: String) {
        val inflater = LayoutInflater.from(context)
        val customView: View = inflater.inflate(R.layout.custom_toast, null)
        val toastMessage: TextView = customView.findViewById(R.id.tvMessage)
        val toastIcon: ImageView = customView.findViewById(R.id.ivToast)
        toastMessage.text = message
        toastIcon.setImageResource(R.drawable.app_logo)
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        customView.startAnimation(animation)
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = customView
        toast.show()
    }*/

    fun showSnackBar(view: View, message: String) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    fun View.setOnClickListeners(delay: Long = 300, action: () -> Unit) {
        this.setOnClickListener {
            this.animate()
                .scaleX(0.85f)
                .scaleY(0.85f)
                .setDuration(200)
                .withEndAction {
                    this.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(50)
                        .start()
                }
                .start()
            this.postDelayed({
                action.invoke()
            }, delay)
        }
    }

    fun View.setOnClickListenerZoom(delay: Long = 300, action: () -> Unit) {
        this.setOnClickListener {
            this.animate()
                .scaleX(1.15f)
                .scaleY(1.15f)
                .setDuration(200)
                .withEndAction {
                    this.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(50)
                        .start()
                }
                .start()
            this.postDelayed({
                action.invoke()
            }, delay)
        }
    }

    fun View.expand(duration: Long = 300) {
        measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = measuredHeight

        layoutParams.height = 0
        visibility = View.VISIBLE

        val valueAnimator = ValueAnimator.ofInt(0, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        valueAnimator.duration = duration
        valueAnimator.start()
    }

    // Extension function to animate collapse
    fun View.collapse(duration: Long = 300) {
        val initialHeight = measuredHeight

        val valueAnimator = ValueAnimator.ofInt(initialHeight, 0)
        valueAnimator.addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        valueAnimator.duration = duration
        valueAnimator.doOnEnd {
            visibility = View.GONE
        }
        valueAnimator.start()
    }

}
package com.pbgames.utils

import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
}
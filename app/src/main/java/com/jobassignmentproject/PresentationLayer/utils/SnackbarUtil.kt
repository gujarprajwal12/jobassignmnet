package com.jobassignmentproject.PresentationLayer.utils

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {

    fun showShort(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showLong(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showWithAction(view: View, message: String, actionText: String, action: () -> Unit) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionText) {
            action.invoke()
        }.show()
    }

    fun showCustom(view: View, message: String, backgroundColor: Int, textColor: Int) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(backgroundColor)
        snackbar.setTextColor(textColor)
        snackbar.show()
    }


    fun showAtTop(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        // Change layout params to show at the top
        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackbarView.layoutParams = params

        snackbar.show()
    }
}

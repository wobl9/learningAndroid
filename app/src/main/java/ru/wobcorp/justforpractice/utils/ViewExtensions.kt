package ru.wobcorp.justforpractice.utils

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        .setBackgroundTint(Color.DKGRAY)
        .show()
}
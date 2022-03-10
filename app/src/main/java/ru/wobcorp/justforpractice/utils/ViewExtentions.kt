package ru.wobcorp.justforpractice.utils

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG)
        .setBackgroundTint(Color.DKGRAY)
        .show()
}
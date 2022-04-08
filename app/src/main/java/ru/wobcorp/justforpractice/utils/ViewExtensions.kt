package ru.wobcorp.justforpractice.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import ru.wobcorp.justforpractice.R

fun showSnackbar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        .setBackgroundTint(Color.DKGRAY)
        .show()
}

fun hideKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun getCornerRadius(context: Context): Int {
    return context.resources.getDimensionPixelSize(R.dimen.corner_radius)
}
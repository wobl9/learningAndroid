package ru.wobcorp.justforpractice.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.replace(
    @IdRes containerId: Int,
    fragment: Fragment
) {
    beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack(null)
        .commit()
}
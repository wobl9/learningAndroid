package ru.wobcorp.justforpractice.utils

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.putArguments(key: String, arg: Int): Fragment {
    val args = Bundle().apply {
        putSerializable(key, arg)
    }
    arguments = args
    return this
}
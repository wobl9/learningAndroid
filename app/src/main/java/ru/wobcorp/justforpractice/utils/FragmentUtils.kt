package ru.wobcorp.justforpractice.utils

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.putArguments(key: String, arg: Int): Fragment {
    return this.apply {
        arguments = Bundle().apply {
            putInt(key, arg)
        }
    }
}
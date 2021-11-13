package ru.wobcorp.justforpractice.presentation.login.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.LoginActivityBinding
import ru.wobcorp.justforpractice.presentation.login.fragment.LoginFragment
import ru.wobcorp.justforpractice.utils.replace

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivityBinding.inflate(layoutInflater).root.let(::setContentView)

        if (savedInstanceState == null) {
            supportFragmentManager.replace(R.id.loginContainer, LoginFragment.newInstance())
        }
    }
}
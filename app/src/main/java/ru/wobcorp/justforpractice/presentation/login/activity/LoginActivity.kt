package ru.wobcorp.justforpractice.presentation.login.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.LoginActivityBinding
import ru.wobcorp.justforpractice.presentation.login.fragment.LoginFragment
import ru.wobcorp.justforpractice.utils.replaceWithoutBackStack

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivityBinding.inflate(layoutInflater).root.let(::setContentView)
        Application.dagger.inject(this)

        if (savedInstanceState == null) {
            supportFragmentManager.replaceWithoutBackStack(
                R.id.loginContainer,
                LoginFragment.newInstance()
            )
        }
    }
}
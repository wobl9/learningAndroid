package ru.wobcorp.justforpractice.presentation.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.wobcorp.justforpractice.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityBinding.inflate(layoutInflater).root.let(::setContentView)
    }
}
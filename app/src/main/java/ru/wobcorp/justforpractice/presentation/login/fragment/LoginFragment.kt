package ru.wobcorp.justforpractice.presentation.login.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.LoginFragmentBinding
import ru.wobcorp.justforpractice.presentation.mainactivity.MainActivity
import ru.wobcorp.justforpractice.utils.SharedPrefHelper
import ru.wobcorp.justforpractice.utils.observe

class LoginFragment : Fragment(R.layout.login_fragment) {

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }

        private const val LOGIN = "LOGIN"
        private const val PASSWORD = "PASSWORD"
        private const val IS_LOGIN = "IS_LOGIN"
    }

    private lateinit var sharedPrefHelper: SharedPrefHelper
    private var _binding: LoginFragmentBinding? = null
    private val binding: LoginFragmentBinding
        get() = _binding!!
    private val viewModel by viewModels<LoginViewModel> { LoginViewModel.Factory() }
    private lateinit var login: String
    private lateinit var password: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LoginFragmentBinding.bind(view)
        sharedPrefHelper = SharedPrefHelper(requireContext())
        addTextChangeListeners()
        observeViewModel()
        binding.apply {
            authButton.setOnClickListener {
                login = binding.etLogin.text.toString()
                password = binding.etPassword.text.toString()
                val isFieldsValid = viewModel.checkUserData(login, password)
                if (isFieldsValid) {
                    checkLoginAndPassword(login, password)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.navigateMainScreen.observe(lifecycleScope) {
            startActivity(MainActivity.getIntent(requireContext()))
        }
        viewModel.errorInputLogin.observe(lifecycleScope) {
            val message = if (it) {
                getString(R.string.error_login)
            } else null
            binding.tilLogin.error = message
        }
        viewModel.errorInputPassword.observe(lifecycleScope) {
            val message = if (it) {
                getString(R.string.error_password)
            } else null
            binding.tilPassword.error = message
        }
    }

    private fun checkLoginAndPassword(login: String, password: String) {
        val isSessionSave = sharedPrefHelper.getBoolean(IS_LOGIN)
        val isLoginCorrect = sharedPrefHelper.getString(LOGIN) == login
        val isPasswordCorrect = sharedPrefHelper.getString(PASSWORD) == password
        if (isSessionSave && isLoginCorrect && isPasswordCorrect) {
            viewModel.onAuthClick()
        } else if (isSessionSave && (!isLoginCorrect || !isPasswordCorrect)) {
            Toast.makeText(
                requireContext(),
                getString(R.string.error_login_or_password),
                Toast.LENGTH_SHORT
            ).show()
        } else if (!isSessionSave) {
            saveSession(login, password)
            viewModel.onAuthClick()
        }
    }

    private fun saveSession(login: String, password: String) {
        sharedPrefHelper.put(LOGIN, login)
        sharedPrefHelper.put(PASSWORD, password)
        sharedPrefHelper.put(IS_LOGIN, true)
    }

    private fun addTextChangeListeners() {
        binding.etLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputLogin()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}
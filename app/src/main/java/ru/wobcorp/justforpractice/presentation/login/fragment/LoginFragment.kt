package ru.wobcorp.justforpractice.presentation.login.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.LoginFragmentBinding
import ru.wobcorp.justforpractice.presentation.filmsactivity.FilmsActivity
import ru.wobcorp.justforpractice.utils.observe
import ru.wobcorp.justforpractice.utils.states.LoginViewState
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.login_fragment) {

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    @Inject
    lateinit var factory: LoginViewModel.Factory
    private var login: String? = null
    private var password: String? = null
    private var _binding: LoginFragmentBinding? = null
    private val binding: LoginFragmentBinding
        get() = _binding!!
    private val viewModel by viewModels<LoginViewModel> { factory }

    override fun onAttach(context: Context) {
        Application.dagger.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LoginFragmentBinding.bind(view)
        binding.apply {
            authButton.setOnClickListener {
                login = etLogin.text?.toString()
                password = etPassword.text?.toString()
                viewModel.checkUserData(login, password)
            }
        }
        viewModel.navigateMainScreen.observe(lifecycleScope) {
            startActivity(FilmsActivity.getIntent(requireContext()))
        }
        viewModel.loginState.observe(lifecycleScope) { loginViewState ->
            renderState(loginViewState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(loginViewState: LoginViewState) {
        when (loginViewState) {
            is LoginViewState.Loading -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.loading_data),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is LoginViewState.Error -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_loading_data),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is LoginViewState.Success -> {
                if (loginViewState.success) {
                    viewModel.onAuthClick()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_login_or_password),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
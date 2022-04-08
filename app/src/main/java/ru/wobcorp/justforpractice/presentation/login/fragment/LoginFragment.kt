package ru.wobcorp.justforpractice.presentation.login.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.wobcorp.justforpractice.Application
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.LoginFragmentBinding
import ru.wobcorp.justforpractice.utils.hideKeyboard
import ru.wobcorp.justforpractice.utils.observe
import ru.wobcorp.justforpractice.utils.showSnackbar
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
            etPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(requireContext(), view)
                    checkUserData()
                }
                true
            }
            authButton.setOnClickListener {
                hideKeyboard(requireContext(), view)
                checkUserData()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkUserData() {
        login = binding.etLogin.text?.toString()
        password = binding.etPassword.text?.toString()
        viewModel.checkUserData(login, password)
        viewModel.loginState.observe(lifecycleScope) { loginViewState ->
            renderState(loginViewState)
        }
    }

    private fun renderState(loginViewState: LoginViewState) {
        when (loginViewState) {
            is LoginViewState.Loading -> {
                showSnackbar(requireView(), getString(R.string.loading_data))
            }
            is LoginViewState.Error -> {
                showSnackbar(requireView(), getString(R.string.error_loading_data))
            }
            is LoginViewState.Success -> {
                if (loginViewState.loginSuccess) {
                    viewModel.onAuthClick()
                } else {
                    showSnackbar(requireView(), getString(R.string.error_login_or_password))
                }
            }
        }
    }
}
package ru.wobcorp.justforpractice.presentation.login.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.wobcorp.justforpractice.R
import ru.wobcorp.justforpractice.databinding.LoginFragmentBinding
import ru.wobcorp.justforpractice.presentation.mainactivity.MainActivity
import ru.wobcorp.justforpractice.utils.observe

class LoginFragment : Fragment(R.layout.login_fragment) {

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    private var binding: LoginFragmentBinding? = null
    private val viewModel by viewModels<LoginViewModel> { LoginViewModel.Factory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)

        binding?.apply {
            authButton.setOnClickListener {
                viewModel.onAuthClick()
            }
        }

        viewModel.navigateMainScreen.observe(lifecycleScope) {
            startActivity(MainActivity.getIntent(requireContext()))
        }
    }
}
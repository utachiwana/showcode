package com.utachiwana.messenger.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.utachiwana.messenger.R
import com.utachiwana.messenger.appComponent
import com.utachiwana.messenger.databinding.FragmentRegisterBinding
import com.utachiwana.messenger.ui.AuthViewModel
import com.utachiwana.messenger.data.network.FConfig
import com.utachiwana.messenger.ui.dialogs.InfoDialog
import javax.inject.Inject

class RegisterFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AuthViewModel.Factory
    private val viewModel: AuthViewModel by activityViewModels {
        viewModelFactory
    }
    private lateinit var binding: FragmentRegisterBinding

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        val view = binding.root

        binding.backArrow.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.helpIv.setOnClickListener {
            InfoDialog().show(childFragmentManager, null)
        }

        binding.nextButton.setOnClickListener {
            viewModel.register(
                binding.loginEt.editableText.toString(),
                binding.passwordEt.editableText.toString(),
                binding.repeatPasswordEt.editableText.toString()
            )
        }
        viewModel.clearState()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.passwordErrorTv.text = ""
            binding.loginErrorTv.text = ""
            when (state) {
                FConfig.REG_ALREADY -> binding.loginErrorTv.text =
                    getString(R.string.login_exists)
                FConfig.REG_INCORRECT_LOGIN -> binding.loginErrorTv.text =
                    getString(R.string.incorrect_login)
                FConfig.REG_INCORRECT_PWD -> binding.passwordErrorTv.text =
                    getString(R.string.incorrect_password)
                FConfig.EMPTY_LOGIN -> binding.loginErrorTv.text =
                    getString(R.string.enter_login)
                FConfig.EMPTY_PWD -> binding.passwordErrorTv.text =
                    getString(R.string.enter_password)
                FConfig.REG_PWD_NOT_REPEAT -> binding.passwordErrorTv.text =
                    getString(R.string.repeat_password_error)
                FConfig.UNEXPECTED_ERROR -> {
                    Toast.makeText(
                        context,
                        getString(R.string.unexpected_error),
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.clearState()
                }
                else -> return@observe
            }
        }
    }
}
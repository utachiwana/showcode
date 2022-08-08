package com.utachiwana.messenger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utachiwana.messenger.R
import com.utachiwana.messenger.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater)
        val view = binding.root
        binding.registerButton.setOnClickListener {
            registerClicked()
        }
        binding.loginButton.setOnClickListener {
            loginClicked()
        }

        return view
    }

    private fun registerClicked() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .replace(R.id.start_frame_layout, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun loginClicked() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .replace(R.id.start_frame_layout, AuthFragment())
            .addToBackStack(null)
            .commit()
    }
}
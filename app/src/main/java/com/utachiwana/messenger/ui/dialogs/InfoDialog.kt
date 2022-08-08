package com.utachiwana.messenger.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.utachiwana.messenger.databinding.DialogInfoBinding

class InfoDialog : DialogFragment() {

    lateinit var binding : DialogInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogInfoBinding.inflate(inflater)
        val view = binding.root
        view.setOnClickListener{
            dismiss()
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return view;
    }
}
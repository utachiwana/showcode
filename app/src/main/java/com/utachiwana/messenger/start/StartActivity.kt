package com.utachiwana.messenger.start

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.utachiwana.messenger.LoadingDialog
import com.utachiwana.messenger.R
import com.utachiwana.messenger.appComponent
import com.utachiwana.messenger.network.FConfig
import com.utachiwana.messenger.main.MainActivity
import com.utachiwana.messenger.start.fragments.AuthFragment
import com.utachiwana.messenger.start.fragments.StartFragment
import javax.inject.Inject

class StartActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AuthViewModel.Factory
    private val viewModel: AuthViewModel by viewModels {
        viewModelFactory
    }

    var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.start_frame_layout, StartFragment())
                .commit()
            viewModel.isAuth()
        }

        viewModel.state.observe(this) { state ->
            if (state == FConfig.AUTH_DONE)
                authDone()
            else if (state == FConfig.REG_DONE)
                registerDone()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                if (loadingDialog == null)
                    loadingDialog = LoadingDialog()
                loadingDialog?.show(supportFragmentManager, null)
            } else
                loadingDialog?.dismiss()
        }

    }

    private fun authDone() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    private fun registerDone() {
        supportFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .add(R.id.start_frame_layout, AuthFragment())
            .commit()
        Toast.makeText(this, getString(R.string.register_successful), Toast.LENGTH_LONG)
    }


}
package com.utachiwana.messenger.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.utachiwana.messenger.ui.dialogs.LoadingDialog
import com.utachiwana.messenger.R
import com.utachiwana.messenger.appComponent
import com.utachiwana.messenger.data.network.FConfig
import com.utachiwana.messenger.ui.AuthViewModel
import com.utachiwana.messenger.ui.fragments.AuthFragment
import com.utachiwana.messenger.ui.fragments.StartFragment
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
        val intent = Intent(this, ComposeMainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    private fun registerDone() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .replace(R.id.start_frame_layout, AuthFragment())
            .addToBackStack(null)
            .commit()
        Toast.makeText(this, getString(R.string.register_successful), Toast.LENGTH_LONG)
    }


}
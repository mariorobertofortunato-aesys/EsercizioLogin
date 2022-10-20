package com.example.eserciziologin.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.eserciziologin.MainActivity
import com.example.eserciziologin.R
import com.example.eserciziologin.databinding.ActivityAuthBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeAuthState()

    }

    /**Check if user is logged and change the UI accordingly*/
    private fun observeAuthState() {

        viewModel.authState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                AuthViewModel.AuthState.AUTHENTICATED -> {
                    /**If user logged in, the button navs to MainActivity*/
                    binding.logoutBtn.isVisible = true
                    binding.logoutBtn.text = "LOGOUT"
                    binding.text.text = "Welcome ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                    binding.loginBtn.text = "START"
                    binding.logoutBtn.setOnClickListener { AuthUI.getInstance().signOut(this) }
                    binding.loginBtn.setOnClickListener {
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else -> {
                    /**Else start the signInFlow*/
                    binding.logoutBtn.isVisible = false
                    binding.text.text = getString(R.string.welcome)
                    binding.loginBtn.text = getString(R.string.login)
                    binding.loginBtn.setOnClickListener {
                        signInFlow()
                    }
                }
            }
        })

    }

    private fun signInFlow() {
        // Give users the option to sign in / register with their email or Google account. If users
        // choose to register with their email, they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        // Create and launch sign-in intent. We listen to the response of this activity with the
        // SIGN_IN_RESULT_CODE code.
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                providers
            ).build(), SIGN_IN_RESULT_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(applicationContext,"Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,"Sign in unsuccessful ${response?.error?.errorCode}", Toast.LENGTH_SHORT).show()
            }
        }
    }





    companion object {
        const val  SIGN_IN_RESULT_CODE = 1001
    }
}
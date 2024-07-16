package com.example.dibamovies.presentation.ui.register

import android.content.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dibamovies.MainActivity
import com.example.dibamovies.databinding.ActivityRegisterBinding
import com.example.dibamovies.presentation.ui.register.validation.ValidationResult
import com.example.dibamovies.shared_component.UiUtils
import com.example.dibamovies.utils.NetworkChecker
import com.example.dibamovies.utils.NetworkUtils
import com.google.android.material.snackbar.Snackbar


class Register : AppCompatActivity() {
    //region Properties
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var broadcastReceiver: BroadcastReceiver
    private var isNetworkConnected = false
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiUtils.removeHeader(this)
        val factory = RegisterViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
        sharedPrefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        initialBinding()
        observeRegistrationResult()
        NetworkUtils.registerNetworkCallback(this, binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkUtils.unregisterNetworkCallback()
    }
    //endregion

    //region Methods
    private fun initialBinding() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val studentNumber = binding.studentNumberEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.registerUser(name, studentNumber, email, password)
        }
    }

    private fun observeRegistrationResult() {
        viewModel.registrationResult.observe(this) { result ->
            when (result) {
                is ValidationResult.Success -> {
                    val editor = sharedPrefs.edit()
                    editor.putBoolean("logged_in", true)
                    editor.apply()
                    showSnackbar("Registration successful!")
                    navigateToHomeScreen()
                }
                is ValidationResult.Error -> {
                    showSnackbar(result.message)
                }
            }
        }
    }

    private fun navigateToHomeScreen() {
        startActivity(Intent(this@Register, MainActivity::class.java))
        finish()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.submitButton, message, Snackbar.LENGTH_LONG).show()
    }

    private fun configBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                checkNetworkConnected()
            }
        }
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        this.registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun checkNetworkConnected() {
        isNetworkConnected = NetworkChecker(this).isInternetConnected
    }
    //endregion
}
package com.example.dibamovies.presentation.ui.register

import android.content.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dibamovies.databinding.ActivityRegisterBinding
import com.example.dibamovies.presentation.ui.homescreen.HomeScreenActivity
import com.example.dibamovies.presentation.ui.register.validation.ValidationResult
import com.example.dibamovies.shared_component.UiUtils
import com.example.dibamovies.utils.NetworkChecker
import com.google.android.material.snackbar.Snackbar


class Register : AppCompatActivity() {

    //region properties
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var broadcastReceiver: BroadcastReceiver
    private var isNetworkConnected = false
    //endregion

    //region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiUtils.removeHeader(this)
        val factory = RegisterViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
        sharedPrefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        initialBinding()
    }
    //endregion

    //region methods
    private fun initialBinding() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val studentNumber = binding.studentNumberEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val editor = sharedPrefs.edit()
            editor.putBoolean("logged_in", true)
            editor.apply()
            showSnackbar("Registration successful!")
            navigateToHomeScreen()

//            Log.e("2323", "before - viewModel")
//            viewModel.registerUser(name, studentNumber, email, password) { result ->
//                Log.e("2324", "before -- viewModel")
//                Log.e("2324", result.toString())
//                when (result) {
//                    is ValidationResult.Success -> {
//                        Log.e("2325", "before --- viewModel")
//                        val editor = sharedPrefs.edit()
//                        editor.putBoolean("logged_in", true)
//                        Log.e("2323", "beroreApply")
//                        editor.apply()
//                        showSnackbar("Registration successful!")
//                        navigateToHomeScreen()
//                    }
//                    is ValidationResult.Error -> {
//                        showSnackbar(result.message)
//                        Log.e("2325", "Validation Error: ${result.message}")
//                    }
//                }
//            }
        }
    }

    private fun navigateToHomeScreen() {
        startActivity(Intent(this@Register, HomeScreenActivity::class.java))
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
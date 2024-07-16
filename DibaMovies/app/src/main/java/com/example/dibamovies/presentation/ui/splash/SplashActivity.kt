package com.example.dibamovies.presentation.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dibamovies.MainActivity
import com.example.dibamovies.R
import com.example.dibamovies.databinding.ActivitySplashBinding
import com.example.dibamovies.presentation.ui.register.Register
import com.example.dibamovies.shared_component.UiUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    //region Properties
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var binding: ActivitySplashBinding
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiUtils.removeHeader(this)
        initialBinding()
        sharedPrefs = getSharedPreferences("auth", Context.MODE_PRIVATE)

//        val editor = sharedPrefs.edit()
//        editor.clear()
//        editor.apply()

        lifecycleScope.launch {
            var state = isLoggedIn()
            delay(5000)
            navigateToNextScreen(state)
        }
    }
    //endregion

    //region Methods
    private fun initialBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(binding.splashImage.context)
            .load(R.drawable.splash_screen_cinema)
            .transition(DrawableTransitionOptions.withCrossFade(2000))
            .into(binding.splashImage)
    }

    private fun navigateToNextScreen(status: Boolean) {
        if (status) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, Register::class.java))
        }
        finish()
    }

    private fun isLoggedIn(): Boolean {
        return sharedPrefs.getBoolean("logged_in", false)
//        return true
    }
    //endregion
}
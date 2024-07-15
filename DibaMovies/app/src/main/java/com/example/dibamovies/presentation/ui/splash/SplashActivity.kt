package com.example.dibamovies.presentation.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dibamovies.R
import com.example.dibamovies.presentation.ui.homescreen.HomeScreenActivity
import com.example.dibamovies.presentation.ui.register.Register
import com.example.dibamovies.shared_component.UiUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    //region properties
    private lateinit var sharedPrefs: SharedPreferences
    //endregion

    //    region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiUtils.removeHeader(this)
        setContentView(R.layout.activity_splash)

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

    //region methods
    private fun navigateToNextScreen(status: Boolean) {
        if (status) {
            startActivity(Intent(this@SplashActivity, HomeScreenActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, Register::class.java))
        }
        finish()
    }

    private fun isLoggedIn(): Boolean {
//        return sharedPrefs.getBoolean("logged_in", false)
        return true
    }
    //endregion
}
package com.example.dibamovies.shared_component

import android.view.View
import androidx.appcompat.app.AppCompatActivity

object UiUtils {
    fun removeHeader(activity: AppCompatActivity) {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        activity.supportActionBar?.hide()
    }
}
package com.example.dibamovies.utils

import android.content.Context
import android.util.Log
import android.view.View
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object NetworkUtils {
    private var networkDisposable: Disposable? = null

    fun registerNetworkCallback(context: Context, rootView: View) {
        networkDisposable?.dispose()

        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                if (!connectivity.available()) {
                    showNetworkErrorSnackbar(rootView)
                }
            }
    }

    fun unregisterNetworkCallback() {
        networkDisposable?.dispose()
    }

    private fun showNetworkErrorSnackbar(rootView: View) {
        Snackbar.make(
            rootView,
            "No internet connection",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("Dismiss") {
                Log.d("NetworkUtils", "Snackbar dismissed by user")
            }
            show()
        }
    }
}
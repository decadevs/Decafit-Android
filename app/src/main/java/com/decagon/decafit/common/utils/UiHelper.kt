package com.decagon.decafit.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.decagon.decafit.R
import com.google.android.material.snackbar.Snackbar


    fun Fragment.snackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(resources.getColor(R.color.primary_color))
            .setTextColor(resources.getColor(R.color.white))
            .show()
    }


fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}
package com.decagon.decafit.common.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.decagon.decafit.R
import com.decagon.decafit.databinding.WorkoutDetailsDialogBinding
import com.decagon.decafit.workout.data.WorkoutItems
import com.google.android.material.snackbar.Snackbar
import java.util.*


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

fun Fragment.showWorkoutDetails(dialogBinding :WorkoutDetailsDialogBinding, workoutItems: WorkoutItems): Dialog {
    val dialog = Dialog(requireContext()).apply {
        setContentView(dialogBinding.root)
        setCancelable(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    dialogBinding.imageView2.setImageResource(R.drawable.workout_detail_background)
    dialogBinding.discTitleTv.text = workoutItems.title
    dialogBinding.workoutDescriptionTv.text = workoutItems.description

    dialogBinding.workoutCloseBtn.setOnClickListener {
        dialog.dismiss()
    }
    return dialog
}

// this is used for navigation animation
fun customNavAnimation(): NavOptions.Builder {
    val navBuilder: NavOptions.Builder = NavOptions.Builder()
    navBuilder.setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
    return navBuilder
}

fun Fragment.onBackPressed(){
    val callBack =object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }
}
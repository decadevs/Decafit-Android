package com.decagon.decafit.common.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.decagon.decafit.R
import com.decagon.decafit.WorkoutsQuery
import com.decagon.decafit.common.common.data.database.model.ReportExercise
import com.decagon.decafit.databinding.ContinueExerciseDialogBinding
import com.decagon.decafit.databinding.LogoutDialogLayoutBinding
import com.decagon.decafit.databinding.WorkoutDetailsDialogBinding
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

fun Fragment.showWorkoutDetails(dialogBinding :WorkoutDetailsDialogBinding, workoutItems: WorkoutsQuery.Exercise): Dialog {
    val dialog = Dialog(requireContext()).apply {
        setContentView(dialogBinding.root)
        setCancelable(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    Glide.with(requireContext()).load(workoutItems.image)
        .into(dialogBinding.detailIV)
    dialogBinding.discTitleTv.text = workoutItems.title
    dialogBinding.workoutDescriptionTv.text = workoutItems.description

    dialogBinding.workoutCloseBtn.setOnClickListener {
        dialog.dismiss()
    }
    return dialog
}


fun Fragment.showReportDetails(dialogBinding :WorkoutDetailsDialogBinding, workoutItems: ReportExercise): Dialog {
    val dialog = Dialog(requireContext()).apply {
        setContentView(dialogBinding.root)
        setCancelable(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    Glide.with(requireContext()).load(workoutItems.image)
        .into(dialogBinding.detailIV)
    dialogBinding.discTitleTv.text = workoutItems.title
    dialogBinding.workoutDescriptionTv.text = workoutItems.description

    dialogBinding.workoutCloseBtn.setOnClickListener {
        dialog.dismiss()
    }
    return dialog
}

fun showLogOutDialog(context: Context, binding: LogoutDialogLayoutBinding, resources: Resources, logOutFunction: () -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(context)
    builder.setView(binding.root)
    builder.setCancelable(false)
    val dialog = builder.create()
    val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
    val height = (resources.displayMetrics.heightPixels * 0.35).toInt()
    dialog!!.window?.setLayout(width, height)

    binding.logoutDialogYesTextView.setOnClickListener {
        logOutFunction.invoke()
        dialog.dismiss()
    }
    binding.logoutDialogNoTextView.setOnClickListener {
        dialog.dismiss()
    }
    return dialog
}

fun Fragment.showChooseToContinueDialog(dialogBinding :ContinueExerciseDialogBinding, showWorkout:()->Unit, showReportWorkout:()->Unit): Dialog {
    val dialog = Dialog(requireContext()).apply {
        setContentView(dialogBinding.root)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    dialogBinding.continueExerciseBtn.setOnClickListener {
        showReportWorkout.invoke()
        dialog.dismiss()
    }
    dialogBinding.startNewExerciseBtn.setOnClickListener {
        showWorkout.invoke()
        dialog.dismiss()
    }
    return dialog
}



fun Fragment.onBackPressed(){
    var pressedTime =0L
    activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                activity?.finish()

            } else {
                Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
            }
            pressedTime = System.currentTimeMillis()
        }

})
}



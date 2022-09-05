package com.decagon.decafit.common.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.decagon.decafit.R

class ProgressBarLoading(context: Context):Dialog(context, R.style.transparent_progress_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progressbar_layout)
        setCancelable(false)
        setCanceledOnTouchOutside(true)
    }
}
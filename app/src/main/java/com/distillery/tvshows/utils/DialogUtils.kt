package com.distillery.tvshows.utils

import android.app.AlertDialog
import android.content.Context
import com.distillery.tvshows.R

fun createAndShowDialog(context: Context,
                        title: String,
                        message: String,
                        onPositiveAction: () -> Unit,
                        onNegativeAction: () -> Unit = {}) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.retry) { dialog, _ ->
            onPositiveAction()
            dialog.dismiss()
        }
        .setNegativeButton(R.string.cancel) { dialog, _ ->
            onNegativeAction()
            dialog.dismiss()
        }
        .create()
        .show()
}

fun createAndShowDialog(context: Context,
                        title: String,
                        message: String,
                        positiveButtonTitle: String,
                        onPositiveAction: () -> Unit,
                        negativeButtonTitle: String = context.getString(R.string.cancel),
                        onNegativeAction: () -> Unit = {}) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonTitle) { dialog, _ ->
            onPositiveAction()
            dialog.dismiss()
        }
        .setNegativeButton(negativeButtonTitle) { dialog, _ ->
            onNegativeAction()
            dialog.dismiss()
        }
        .create()
        .show()
}
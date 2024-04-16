package com.nimesh.interviewtask.Utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
fun permissionDialog(
    context: Context,
    title: String,
    description: String,
    positiveText: String?,
    negativeText: String?,
    positiveAction: Runnable?,
    negativeAction: Runnable?
) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(description)
        .setNegativeButton(negativeText) { dialog, _ ->
            negativeAction?.run()
            dialog.dismiss()
        }
        .setPositiveButton(positiveText) { dialog, _ ->
            positiveAction?.run()
            dialog.dismiss()
        }
        .show()
}

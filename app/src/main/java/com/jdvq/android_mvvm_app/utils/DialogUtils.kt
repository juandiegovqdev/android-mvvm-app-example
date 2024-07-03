package com.jdvq.android_mvvm_app.utils

import android.app.AlertDialog
import android.content.Context
import com.jdvq.android_mvvm_app.R

fun showDialog(context: Context, title: String, message: String) {
    val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setButton(
        AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok)
    ) { dialog, _ ->
        run {
            dialog.dismiss()
        }
    }
    alertDialog.show()
}

fun showExitDialog(context: Context, title:String) {
    val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
    alertDialog.setTitle(title)
    alertDialog.setButton(
        AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok)
    ) { dialog, _ ->
        run {
            System.exit(1)
            dialog.dismiss()
        }
    }
    alertDialog.show()
}
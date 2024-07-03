package com.jdvq.android_mvvm_app.utils

import androidx.appcompat.widget.AppCompatEditText

fun validateEditText(appCompatEditText: AppCompatEditText): Boolean {
    return appCompatEditText.text!!.isBlank() || appCompatEditText.text!!.isEmpty()
}
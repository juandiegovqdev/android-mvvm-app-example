package com.jdvq.android_mvvm_app.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ObjectModel(
    var id: Long,
    val name: String,
    val description: String,
    val type: String
) : Parcelable
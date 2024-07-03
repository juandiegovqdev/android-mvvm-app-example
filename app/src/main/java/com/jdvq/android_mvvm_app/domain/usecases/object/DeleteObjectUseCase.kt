package com.jdvq.android_mvvm_app.domain.usecases.`object`

import android.annotation.SuppressLint
import com.jdvq.android_mvvm_app.domain.database.daos.ObjectDao
import com.jdvq.android_mvvm_app.domain.models.ObjectModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteObjectUseCase
@Inject
constructor(
    private val objectDao: ObjectDao
) {
    @SuppressLint("Recycle")
    suspend operator fun invoke(objectModel: ObjectModel) {
        return withContext(Dispatchers.IO) {
            objectDao.deleteObjectById(objectModel.id)
        }
    }
}
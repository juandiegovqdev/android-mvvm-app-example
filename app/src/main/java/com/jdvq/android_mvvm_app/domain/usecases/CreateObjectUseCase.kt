package com.jdvq.android_mvvm_app.domain.usecases

import android.annotation.SuppressLint
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.domain.database.daos.ObjectDao
import com.jdvq.android_mvvm_app.domain.mapper.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateObjectUseCase
@Inject
constructor(
    private val objectDao: ObjectDao
) {
    @SuppressLint("Recycle")
    suspend operator fun invoke() {
        return withContext(Dispatchers.IO) {
            objectDao.insert(ObjectMapper().mapToEntity(GlobalVariables.currentObject))
        }
    }
}
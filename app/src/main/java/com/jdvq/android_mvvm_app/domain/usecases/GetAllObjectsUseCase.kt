package com.jdvq.android_mvvm_app.domain.usecases

import android.annotation.SuppressLint
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.domain.database.daos.ObjectDao
import com.jdvq.android_mvvm_app.domain.mapper.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllObjectsUseCase
@Inject constructor(
    private val objectDao: ObjectDao
) {
    @SuppressLint("Recycle")
    suspend operator fun invoke() {
        return withContext(Dispatchers.IO) {
            GlobalVariables.objects.clear()
            GlobalVariables.objects.addAll(ObjectMapper().mapListToDomainModels(objectDao.getAll()))
        }
    }
}
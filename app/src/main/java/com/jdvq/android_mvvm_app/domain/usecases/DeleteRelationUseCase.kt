package com.jdvq.android_mvvm_app.domain.usecases

import android.annotation.SuppressLint
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.domain.database.daos.ObjectDao
import com.jdvq.android_mvvm_app.domain.database.daos.RelationDao
import com.jdvq.android_mvvm_app.domain.entities.RelationEntity
import com.jdvq.android_mvvm_app.domain.mapper.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteRelationUseCase
@Inject
constructor(
    private val relationDao: RelationDao
) {
    @SuppressLint("Recycle")
    suspend operator fun invoke(relationEntity: RelationEntity) {
        return withContext(Dispatchers.IO) {
            relationDao.deleteRelationById(relationEntity.id)
        }
    }
}
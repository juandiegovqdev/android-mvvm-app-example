package com.jdvq.android_mvvm_app.domain.usecases.relations

import android.annotation.SuppressLint
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.domain.database.daos.RelationDao
import com.jdvq.android_mvvm_app.domain.models.ObjectModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRelationUseCase
@Inject constructor(
    private val relationDao: RelationDao
) {
    @SuppressLint("Recycle")
    suspend operator fun invoke(objectModel: ObjectModel) {
        return withContext(Dispatchers.IO) {
            GlobalVariables.relations.clear()
            GlobalVariables.relations =
                relationDao.getRelationsForObject(objectModel.id).toMutableList()
        }
    }
}
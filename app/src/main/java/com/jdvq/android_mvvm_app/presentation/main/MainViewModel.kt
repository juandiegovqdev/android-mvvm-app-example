package com.jdvq.android_mvvm_app.presentation.main

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.domain.adapters.ObjectAdapter
import com.jdvq.android_mvvm_app.domain.adapters.RelationAdapter
import com.jdvq.android_mvvm_app.domain.entities.RelationEntity
import com.jdvq.android_mvvm_app.domain.models.ObjectModel
import com.jdvq.android_mvvm_app.domain.usecases.`object`.CreateObjectUseCase
import com.jdvq.android_mvvm_app.domain.usecases.`object`.DeleteObjectUseCase
import com.jdvq.android_mvvm_app.domain.usecases.`object`.GetAllObjectsUseCase
import com.jdvq.android_mvvm_app.domain.usecases.relations.DeleteRelationUseCase
import com.jdvq.android_mvvm_app.domain.usecases.relations.GetRelationUseCase
import com.jdvq.android_mvvm_app.domain.usecases.relations.InsertRelationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val createObjectUseCase: CreateObjectUseCase,
    private val getAllObjectsUseCase: GetAllObjectsUseCase,
    private val deleteObjectUseCase: DeleteObjectUseCase,
    private val insertRelationUseCase: InsertRelationUseCase,
    private val deleteRelationUseCase: DeleteRelationUseCase,
    private val getRelationUseCase: GetRelationUseCase
) : ViewModel() {

    lateinit var objectAdapter: ObjectAdapter
    lateinit var relationAdapter: RelationAdapter
    private val _objectModel = MutableLiveData<ObjectModel>()
    val objectModel: LiveData<ObjectModel> get() = _objectModel

    fun setObjectModel(objectModel: ObjectModel) {
        _objectModel.value = objectModel
    }

    fun insertObject() {
        viewModelScope.launch {
            createObjectUseCase.invoke()
        }
    }

    fun getAllObject() {
        viewModelScope.launch {
            getAllObjectsUseCase.invoke()
        }
    }

    fun deleteObjectById(objectModel: ObjectModel) {
        viewModelScope.launch {
            deleteObjectUseCase.invoke(objectModel)
        }
    }

    fun getRelation(objectModel: ObjectModel) {
        viewModelScope.launch {
            getRelationUseCase.invoke(objectModel)
        }
    }

    fun deleteRelation(relationEntity: RelationEntity) {
        viewModelScope.launch {
            deleteRelationUseCase.invoke(relationEntity)
        }
    }

    fun onDialogChildSelected(selectedChild: ObjectModel) {
        val parentId = objectModel.value?.id ?: 0
        val newRelation = RelationEntity(parentId = parentId, childId = selectedChild.id)
        insertRelation(newRelation)
        relationAdapter.updateRelations(GlobalVariables.relations)
    }

    private fun insertRelation(relationEntity: RelationEntity) {
        viewModelScope.launch {
            insertRelationUseCase.invoke(relationEntity)
        }
    }

    fun setupObjectAdapter(context: Context, activity: FragmentActivity) {
        getAllObject()
        objectAdapter = ObjectAdapter(context, this, activity)
        objectAdapter.setObjects(GlobalVariables.objects)
    }

    fun loadObjects(excludeObjectId: Long): List<ObjectModel> {
        return GlobalVariables.objects.filter { it.id != excludeObjectId }
    }
}
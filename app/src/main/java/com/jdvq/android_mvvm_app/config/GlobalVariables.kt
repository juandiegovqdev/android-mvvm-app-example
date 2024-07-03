package com.jdvq.android_mvvm_app.config

import com.jdvq.android_mvvm_app.domain.entities.RelationEntity
import com.jdvq.android_mvvm_app.domain.models.ObjectModel

class GlobalVariables {
    companion object {
        var objects = mutableListOf<ObjectModel>()
        var relations = mutableListOf<RelationEntity>()
        lateinit var currentObject: ObjectModel
    }
}
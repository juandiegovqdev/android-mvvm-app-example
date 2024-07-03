package com.jdvq.android_mvvm_app.domain.mapper

import com.jdvq.android_mvvm_app.domain.entities.ObjectEntity
import com.jdvq.android_mvvm_app.domain.models.ObjectModel

class ObjectMapper {

    fun mapToEntity(model: ObjectModel): ObjectEntity {
        return ObjectEntity(
            id = model.id, type = model.type, name = model.name, description = model.description
        )
    }

    fun mapToDomainModel(entity: ObjectEntity): ObjectModel {
        return ObjectModel(
            id = entity.id,
            type = entity.type,
            name = entity.name,
            description = entity.description,
        )
    }

    fun mapListToEntities(domainModels: List<ObjectModel>): List<ObjectEntity> {
        return domainModels.map { accelerometerModel ->
            mapToEntity(accelerometerModel)
        }
    }

    fun mapListToDomainModels(entities: List<ObjectEntity>): List<ObjectModel> {
        return entities.map { accelerometerEntity ->
            mapToDomainModel(accelerometerEntity)
        }
    }
}

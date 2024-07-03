package com.jdvq.android_mvvm_app.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jdvq.android_mvvm_app.domain.entities.ObjectEntity
import com.jdvq.android_mvvm_app.domain.entities.RelationEntity
import com.jdvq.android_mvvm_app.domain.models.ObjectModel

@Dao
interface ObjectDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: ObjectEntity)

    @Query("SELECT * FROM objects WHERE name LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchObjects(searchQuery: String): List<ObjectEntity>

    @Query("SELECT * FROM objects")
    fun getAll(): List<ObjectEntity>

    @Query("DELETE FROM objects WHERE id = :objectId")
    fun deleteObjectById(objectId: Long)
}

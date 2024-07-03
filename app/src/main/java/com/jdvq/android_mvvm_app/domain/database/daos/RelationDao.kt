package com.jdvq.android_mvvm_app.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdvq.android_mvvm_app.domain.entities.RelationEntity

@Dao
interface RelationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRelation(relation: RelationEntity): Long

    @Query("DELETE FROM relations WHERE id = :relationId")
    fun deleteRelationById(relationId: Long)

    @Query("SELECT * FROM relations WHERE parentId = :parentId")
    fun getRelationsForObject(parentId: Long): List<RelationEntity>
}

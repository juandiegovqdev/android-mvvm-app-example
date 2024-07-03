package com.jdvq.android_mvvm_app.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jdvq.android_mvvm_app.config.Constants
import com.jdvq.android_mvvm_app.domain.database.daos.ObjectDao
import com.jdvq.android_mvvm_app.domain.database.daos.RelationDao
import com.jdvq.android_mvvm_app.domain.entities.ObjectEntity
import com.jdvq.android_mvvm_app.domain.entities.RelationEntity
import com.jdvq.android_mvvm_app.utils.DateConverter

@Database(
    entities = [ObjectEntity::class, RelationEntity::class], version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun objectDao(): ObjectDao
    abstract fun relationDao(): RelationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, Constants.DB
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
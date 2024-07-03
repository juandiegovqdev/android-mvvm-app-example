package com.jdvq.android_mvvm_app

import android.app.Application
import androidx.room.Room
import com.jdvq.android_mvvm_app.config.Constants
import com.jdvq.android_mvvm_app.domain.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            Constants.DB
        ).build()
    }
}

package com.jdvq.android_mvvm_app.di

import android.app.Application
import android.content.Context
import com.jdvq.android_mvvm_app.domain.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun injectObjectDao(
        database: AppDatabase
    ) = database.objectDao()

    @Singleton
    @Provides
    fun injectRelationDao(
        database: AppDatabase
    ) = database.relationDao()
}
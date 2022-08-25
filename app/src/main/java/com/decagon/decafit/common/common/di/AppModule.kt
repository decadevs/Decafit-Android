package com.decagon.decafit.common.common.di

import android.content.Context
import com.decagon.decafit.common.common.data.database.AppDatabase
import com.decagon.decafit.common.common.data.database.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getAppDb(@ApplicationContext context: Context): AppDatabase{
        return AppDatabase.getAppDatabase(context)
    }

    @Singleton
    @Provides
    fun getDao(appDb: AppDatabase): WorkoutDao{
        return appDb.getWorkOutDao()

    }
}
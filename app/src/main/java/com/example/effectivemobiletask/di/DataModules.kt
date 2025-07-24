package com.example.effectivemobiletask.di

import android.content.Context
import androidx.room.Room
import com.example.effectivemobiletask.data.CourseRepository
import com.example.effectivemobiletask.data.CourseRepositoryImpl
import com.example.effectivemobiletask.data.source.local.AppDatabase
import com.example.effectivemobiletask.data.source.local.CourseDao
import com.example.effectivemobiletask.data.source.network.NetworkDataSource
import com.example.effectivemobiletask.data.source.network.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCourseRepository(repository: CourseRepositoryImpl): CourseRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {
    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: NetworkDataSourceImpl): NetworkDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase.db",
            )
            .build()
    }

    @Provides fun provideTaskDao(database: AppDatabase): CourseDao = database.courseDao()
}

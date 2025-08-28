package com.example.effectivemobiletask.di

import android.content.Context
import androidx.room.Room
import com.example.effectivemobiletask.data.local.AppDatabase
import com.example.effectivemobiletask.data.local.CourseDao
import com.example.effectivemobiletask.data.network.NetworkRepository
import com.example.effectivemobiletask.data.network.NetworkRepositoryImpl
import com.example.effectivemobiletask.data.repository.CourseRepository
import com.example.effectivemobiletask.data.repository.CourseRepositoryImpl
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
    abstract fun bindCourseRepository(
        repository: CourseRepositoryImpl
    ): CourseRepository

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(
        repository: NetworkRepositoryImpl
    ): NetworkRepository
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

    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase): CourseDao = database.courseDao()

/*    @Provides
    @Singleton
    fun bindCourseRepository(
        courseDao: CourseDao,
        networkRepository: NetworkRepository,
    ): CourseRepository =
        CourseRepositoryImpl(
            localDataSource = courseDao,
            networkRepository = networkRepository,
        )

    @Provides
    @Singleton
    fun bindNetworkRepository(okHttpClient: OkHttpClient): NetworkRepository =
        NetworkRepositoryImpl(okHttpClient)*/
}

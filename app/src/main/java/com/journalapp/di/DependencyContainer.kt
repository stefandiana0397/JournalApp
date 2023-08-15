package com.journalapp.di

import android.app.Application
import com.journalapp.data.EntriesDataSource
import com.journalapp.data.repository.JournalRepositoryImpl
import com.journalapp.domain.repository.JournalRepository
import com.journalapp.domain.usecase.GetDailyGratitudeEntries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyContainer {

    @Provides
    @Singleton
    fun provideGetDailyGratitudeEntries(repository: JournalRepository): GetDailyGratitudeEntries {
        return GetDailyGratitudeEntries(repository)
    }

    @Provides
    @Singleton
    fun provideJournalRepository(
        dataSource: EntriesDataSource
    ): JournalRepository {
        return JournalRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideDataSource(app: Application): EntriesDataSource {
        return EntriesDataSource(app)
    }

//    @Provides
//    @Singleton
//    fun provideLocalDatabase(app: Application): LocalDatabase {
//        return Room.databaseBuilder(
//            app,
//            LocalDatabase::class.java,
//            "local_db",
//        ).build()
//    }
}

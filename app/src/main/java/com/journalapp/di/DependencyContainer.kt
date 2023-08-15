package com.journalapp.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.journalapp.data.local.converter.StringListConverter
import com.journalapp.data.local.converter.TagConverter
import com.journalapp.data.local.datasource.LocalDatabase
import com.journalapp.data.remote.EntriesDataSource
import com.journalapp.data.repository.JournalRepositoryImpl
import com.journalapp.data.util.GsonParser
import com.journalapp.domain.repository.JournalRepository
import com.journalapp.domain.usecase.GetDailyGratitudeEntries
import com.journalapp.domain.usecase.LoadJournal
import com.journalapp.presentation.MainViewModel
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
    fun provideLoadJournal(repository: JournalRepository): LoadJournal {
        return LoadJournal(repository)
    }

    @Provides
    @Singleton
    fun provideJournalRepository(
        dataSource: EntriesDataSource,
        db: LocalDatabase
    ): JournalRepository {
        return JournalRepositoryImpl(dataSource, db.journalDao)
    }

    @Provides
    @Singleton
    fun provideDataSource(app: Application): EntriesDataSource {
        return EntriesDataSource(app)
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(app: Application): LocalDatabase {
        return Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            "local_db"
        ).addTypeConverter(StringListConverter(GsonParser(Gson())))
            .addTypeConverter(TagConverter(GsonParser(Gson())))
            .build()
    }
}

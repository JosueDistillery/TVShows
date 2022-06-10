package com.distillery.tvshows.di

import android.content.Context
import com.distillery.tvshows.database.TVDatabase
import com.distillery.tvshows.database.converters.GenresConverter
import com.distillery.tvshows.database.dao.FavoriteTVShowDao
import com.distillery.tvshows.repository.FavoritesRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**
     * Provide [GenresConverter]
     */
    @Provides
    fun provideGenresConverter(gson: Gson): GenresConverter = GenresConverter(gson)

    /**
     * Build [TVDatabase]
     */
    @Provides
    @Singleton
    fun provideTVDatabase(
        @ApplicationContext context: Context,
        genresConverter: GenresConverter
    ): TVDatabase {
        return TVDatabase.buildDatabase(context, genresConverter)
    }

    /**
     * Provide [FavoriteTVShowDao]
     */
    @Provides
    fun provideFavoriteTVShowDao(tvDatabase: TVDatabase): FavoriteTVShowDao = tvDatabase.favoriteTVShowDao()

    /**
     * Provide [FavoritesRepository]
     */
    @Provides
    fun provideFavoritesRepository(favoriteTVShowDao: FavoriteTVShowDao): FavoritesRepository =
        FavoritesRepository.buildRepository(favoriteTVShowDao)
}
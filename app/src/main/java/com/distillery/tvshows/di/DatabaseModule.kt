package com.distillery.tvshows.di

import android.content.Context
import com.distillery.tvshows.database.TVDatabase
import com.distillery.tvshows.database.converters.GenresConverter
import com.distillery.tvshows.database.dao.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /**
     * Provide [GenresConverter]
     */
    @Provides
    @Singleton
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
     * Provide [FavoriteDao]
     */
    @Provides
    @Singleton
    fun provideTVShowDao(tvDatabase: TVDatabase): TVShowDao = tvDatabase.tvShowDao()

    /**
     * Provide [FavoriteDao]
     */
    @Provides
    @Singleton
    fun provideFavoriteTVShowDao(tvDatabase: TVDatabase): FavoriteDao = tvDatabase.favoriteTVShowDao()
}
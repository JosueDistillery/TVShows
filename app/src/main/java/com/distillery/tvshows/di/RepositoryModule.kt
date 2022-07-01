package com.distillery.tvshows.di


import com.distillery.tvshows.database.dao.FavoriteTVShowDao
import com.distillery.tvshows.repository.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**
     * Provide [FavoritesRepository]
     */
    @Provides
    @Singleton
    fun provideFavoritesRepository(favoriteTVShowDao: FavoriteTVShowDao): FavoritesRepository =
        FavoritesRepository.buildRepository(favoriteTVShowDao)
}
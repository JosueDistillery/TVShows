package com.distillery.tvshows.di


import com.distillery.tvshows.database.dao.FavoriteDao
import com.distillery.tvshows.database.dao.TVShowDao
import com.distillery.tvshows.network.TVApi
import com.distillery.tvshows.repository.FavoriteRepository
import com.distillery.tvshows.repository.TVShowRepository
import com.distillery.tvshows.utils.helpers.ConnectivityHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**
     * Provide [FavoriteRepository]
     */
    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteDao: FavoriteDao
    ): FavoriteRepository = FavoriteRepository.buildRepository(favoriteDao)

    /**
     * Provide [TVShowRepository]
     */
    @Provides
    @Singleton
    fun provideTVShowRepository(
        tvApi: TVApi,
        tvShowDao: TVShowDao,
        connectivity: ConnectivityHelper
    ): TVShowRepository = TVShowRepository.buildRepository(tvApi, tvShowDao, connectivity)
}
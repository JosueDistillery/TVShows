package com.distillery.tvshows.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.distillery.tvshows.data.entity.FavoriteTVShow
import com.distillery.tvshows.database.TVDatabase.Companion.DATABASE_VERSION
import com.distillery.tvshows.database.converters.GenresConverter
import com.distillery.tvshows.database.dao.FavoriteTVShowDao

@Database(
    entities = [FavoriteTVShow::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(GenresConverter::class)
abstract class TVDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1

        private const val DATABASE_NAME = "TVShows_Database"

        fun buildDatabase(
            context: Context,
            genresConverter: GenresConverter
        ): TVDatabase {
            return Room.databaseBuilder(context, TVDatabase::class.java,DATABASE_NAME)
                .addTypeConverter(genresConverter)
                .build()
        }
    }

    abstract fun favoriteTVShowDao(): FavoriteTVShowDao
}
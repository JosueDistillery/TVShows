package com.distillery.tvshows.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class GenresConverter(private val gson: Gson){

    @TypeConverter
    fun fromGenres(list:List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toGenres(json: String): List<String> {
        val typeToken = object : TypeToken<List<String>>() {}.type

        return try {
            gson.fromJson(json, typeToken)
        }catch (error: Throwable){
            emptyList()
        }
    }
}
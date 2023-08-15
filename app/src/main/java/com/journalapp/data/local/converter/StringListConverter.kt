package com.journalapp.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.journalapp.data.util.JsonParser

@ProvidedTypeConverter
class StringListConverter(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromString(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return jsonParser.toJson(
            stringList,
            object : TypeToken<ArrayList<String>>() {}.type
        ) ?: "[]"
    }
}

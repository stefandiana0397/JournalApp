package com.journalapp.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.journalapp.data.util.JsonParser
import com.journalapp.domain.model.Tag

@ProvidedTypeConverter
class TagConverter(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromString(json: String): List<Tag> {
        return jsonParser.fromJson(
            json,
            object : TypeToken<ArrayList<Tag>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toString(stringList: List<Tag>): String {
        return jsonParser.toJson(
            stringList,
            object : TypeToken<ArrayList<Tag>>() {}.type
        ) ?: "[]"
    }
}

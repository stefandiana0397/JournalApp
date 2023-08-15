package com.journalapp.domain.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class Tag(
    val name: String
)

class TagDeserializer : JsonDeserializer<Tag> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Tag {
        val tagString = json?.asString ?: ""
        return Tag(tagString)
    }
}
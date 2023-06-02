package com.test.myapplication.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "chat_gpt"
)

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class ChatGpt(
    @PrimaryKey
    @field:Json(name = "id")
    var id: Int = 0,
    @field:Json(name = "text")
    var text: String?,
    @field:Json(name = "view_type")
    var viewType: Int = 0,
    @field:Json(name = "created_at")
    var createdAt: String?,
    @field:Json(name = "url")
    var url: String = "",
) : Parcelable

fun getListOfChatItems() = mutableListOf(
    ChatGpt(0, "Hello what are you doing?", 0, "", ""),
    ChatGpt(0, "I am fine. How about you?", 1, "", ""),
    ChatGpt(0, "So what you are uppto at the moment?", 0, "", "")
)

fun getChatGptObj(text: String) = ChatGpt(0, text, 0, "", "")

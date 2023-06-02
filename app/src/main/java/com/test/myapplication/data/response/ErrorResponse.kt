package com.test.myapplication.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ErrorResponse {

    @Json(name = "message") var message: String? = null
    @Json(name = "data") var data: ErrorData? = null

    @JsonClass(generateAdapter = true)
    class ErrorData
}
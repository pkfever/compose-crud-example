package com.test.myapplication.data.response

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class BaseResponse<T>() : Parcelable {
    @Json(name = "message")
    var message: String? = null

    @Json(name = "status")
    var status: Boolean? = null

    @Json(name = "data")
    var data: T? = null

    @Json(name = "cursor")
    var cursor: String? = null

    constructor(parcel: Parcel) : this() {
        message = parcel.readString()
        cursor = parcel.readString()
        status = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeValue(status)
        parcel.writeString(cursor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseResponse<Parcelable>> {
        override fun createFromParcel(parcel: Parcel): BaseResponse<Parcelable> {
            return BaseResponse(parcel)
        }

        override fun newArray(size: Int): Array<BaseResponse<Parcelable>?> {
            return arrayOfNulls(size)
        }
    }
}
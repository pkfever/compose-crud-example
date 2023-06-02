package com.test.myapplication.data.model

import android.text.TextUtils
import androidx.annotation.Keep

private const val NO_CONNECTION_ERROR_MESSAGE = "No Internet Connection!"
private const val BAD_RESPONSE_ERROR_MESSAGE = "Bad response!"
private const val TIME_OUT_ERROR_MESSAGE = "Time out!"
private const val EMPTY_RESPONSE_ERROR_MESSAGE = "Empty response!"
private const val NOT_DEFINED_ERROR_MESSAGE = "Not defined!"
private const val UNAUTHORIZED_ERROR_MESSAGE = "Unauthorized!"
private const val VALIDATION_ERROR_MESSAGE = "Bad Validation"
private const val TOO_MANY_REQUESTS = "Too Many Requests"
private const val INTERNAL_ERROR="Internal Error"

@Keep
data class ErrorModel(
    var message: String?,
    val code: Int?,
    var errorStatus: ErrorStatus,
) {

    constructor(message: String?, errorStatus: ErrorStatus) : this(message, null, errorStatus)

    init {
        if (TextUtils.isEmpty(message))
            this.message =
                if (errorStatus.equals(ErrorStatus.UNPROCESSED_DATA)) message else getErrorMessage()
    }

    private fun getErrorMessage(): String {
        return when (errorStatus) {
            ErrorStatus.NO_CONNECTION -> NO_CONNECTION_ERROR_MESSAGE
            ErrorStatus.BAD_RESPONSE -> BAD_RESPONSE_ERROR_MESSAGE
            ErrorStatus.TIMEOUT -> TIME_OUT_ERROR_MESSAGE
            ErrorStatus.EMPTY_RESPONSE -> EMPTY_RESPONSE_ERROR_MESSAGE
            ErrorStatus.NOT_DEFINED -> NOT_DEFINED_ERROR_MESSAGE
            ErrorStatus.UNAUTHORIZED -> UNAUTHORIZED_ERROR_MESSAGE
            ErrorStatus.UNPROCESSED_DATA -> VALIDATION_ERROR_MESSAGE
            ErrorStatus.TOO_MANY_REQUESTS -> TOO_MANY_REQUESTS
            ErrorStatus.INTERNAL_ERROR->INTERNAL_ERROR
        }
    }

    /**
     * various error status to know what happened if something goes wrong with a repository call
     */
    enum class ErrorStatus {
        /**
         * error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,

        /**
         * error in getting value (Json Error, Server Error, etc)
         */
        BAD_RESPONSE,

        /**
         * Internal  error
         */

        INTERNAL_ERROR,

        /**
         * Time out  error
         */
        TIMEOUT,

        /**
         * no data available in repository
         */
        EMPTY_RESPONSE,

        /**
         * an unexpected error
         */
        NOT_DEFINED,

        /**
         * bad credential
         */
        UNAUTHORIZED,

        //when provided data in invalid
        UNPROCESSED_DATA,

        //to many requests
        TOO_MANY_REQUESTS
    }
}
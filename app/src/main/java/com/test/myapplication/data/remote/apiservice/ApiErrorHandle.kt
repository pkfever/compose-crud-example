package com.test.myapplication.data.remote.apiservice

import com.squareup.moshi.Moshi
import com.test.myapplication.data.model.ErrorModel
import com.test.myapplication.data.response.ErrorResponse
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

val moshi: Moshi = Moshi.Builder().build()
fun convertErrorBody(throwable: HttpException): ErrorResponse? =
    throwable.response()?.errorBody()?.let {
        try {
            moshi.adapter(ErrorResponse::class.java).fromJson(it.string())
            //gson.getAdapter(ErrorResponse::class.java).fromJson(it.string())
        } catch (ex: java.lang.Exception) {
            val errResponse = ErrorResponse()
            errResponse.message = ""
            return@let errResponse
        }
    }


class ApiErrorHandle {

    fun traceErrorException(throwable: Throwable?): ErrorModel {
        val errorModel: ErrorModel? = when (throwable) {

            // if throwable is an instance of HttpException
            // then attempt to parse error data from response body
            is HttpException -> {


                // handle UNAUTHORIZED situation (when token expired)
                if (throwable.code() == 403) {

                    ErrorModel(
                        throwable.message(),
                        throwable.code(),
                        ErrorModel.ErrorStatus.UNAUTHORIZED
                    )

                } else if (throwable.code() == 422) {

                    ErrorModel(
                        throwable.response()?.errorBody()?.string(),
                        throwable.code(),
                        ErrorModel.ErrorStatus.UNPROCESSED_DATA
                    )

                } else if (throwable.code() == 429) {

                    ErrorModel(
                        throwable.response()?.errorBody()?.string(),
                        throwable.code(),
                        ErrorModel.ErrorStatus.TOO_MANY_REQUESTS
                    )

                } else {
                    getHttpError(throwable)
                }
            }

            // handle api call timeout error
            is SocketTimeoutException, is SocketException -> {
                ErrorModel(throwable.message, 408, ErrorModel.ErrorStatus.TIMEOUT)
            }

            is UnknownHostException -> {
                ErrorModel("", 503, ErrorModel.ErrorStatus.NO_CONNECTION)
            }

            // handle connection error
            is IOException -> {
                ErrorModel(throwable.message, ErrorModel.ErrorStatus.NO_CONNECTION)
            }
            is InternalError -> {
                ErrorModel(throwable.message, 422, ErrorModel.ErrorStatus.INTERNAL_ERROR)
            }


            else -> null
        }
        return errorModel ?: ErrorModel(
            throwable?.localizedMessage ?: "No Defined Error!",
            0,
            ErrorModel.ErrorStatus.BAD_RESPONSE
        )
    }

    /**
     * attempts to parse http response body and get error data from it
     *
     * @param body retrofit response body
     * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
     */
    private fun getHttpError(body: HttpException): ErrorModel {
        return try {
            // use response body to get error detail
            val errorResponse = convertErrorBody(body)
            ErrorModel(errorResponse?.message, 400, ErrorModel.ErrorStatus.BAD_RESPONSE)
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(message = e.message, errorStatus = ErrorModel.ErrorStatus.NOT_DEFINED)
        }

    }


}
package com.jobassignmentproject.NetworkLayer

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException) {
        ApiResult.Error("Server Error: ${e.message}")
    } catch (e: IOException) {
        ApiResult.Error("Network Error: Check your internet connection.")
    } catch (e: Exception) {
        ApiResult.Error("Unexpected Error: ${e.localizedMessage}")
    }
}

package com.eco4ndly.githubtrendingrepo.data.api

/**
 * A Sayan Porya code on 15/03/20
 *
 * Api Response generic sealed class
 */
sealed class ApiResult<out T: Any> {
  /**
   * Success Response with data class
   */
  data class Success<out T: Any>(val data: T): ApiResult<T>()

  /**
   * Error response from API or any other error
   */
  data class Error(
    val exception: Throwable,
    val message: String = exception.message?: ErrorHandler.UNKNOWN_ERROR
  ): ApiResult<Nothing>()

  /**
   * Loading state while making the api call
   */
  data class Loading(val isLoading: Boolean): ApiResult<Nothing>()
}
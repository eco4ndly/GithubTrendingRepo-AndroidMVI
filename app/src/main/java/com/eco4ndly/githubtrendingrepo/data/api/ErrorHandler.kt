package com.eco4ndly.githubtrendingrepo.data.api

import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent.inject
/**
 * A Sayan Porya code on 15/03/20
 */

object ErrorHandler {
  const val UNKNOWN_ERROR = "An unknown error occurred!"

  inline fun <reified T> parseError(responseBody: ResponseBody?): T? {

    responseBody?.string()?.run {
      try {
        val moshi by inject(Moshi::class.java)
        val errorJsonAdapter = moshi.adapter(T::class.java)
        return errorJsonAdapter.fromJson(this)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
    return null
  }
}
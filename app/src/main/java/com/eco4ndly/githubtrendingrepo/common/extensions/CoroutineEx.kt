package com.eco4ndly.githubtrendingrepo.common.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.CheckResult
import com.eco4ndly.githubtrendingrepo.common.Utils
import com.eco4ndly.githubtrendingrepo.data.api.ApiResult
import com.eco4ndly.githubtrendingrepo.data.api.NetworkErrorMessages
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

/**
 * A Sayan Porya code on 2020-02-08
 */

/**
 * Common tasks for coroutine while making network calls
 *  - Checking Exceptions and act accordingly
 *  - Emitting show loader state at the beginning
 *  - Emitting hide loader state at the end of call
 *  - Emitting error at the end as exhaust
 */
@ExperimentalCoroutinesApi
suspend fun <T : Any> Flow<ApiResult<T>>.applyCommonStuffs(dispatcher: CoroutineDispatcher = Dispatchers.IO): Flow<ApiResult<T>> {

  fun convertErrorBody(exception: HttpException): String? {
    return try {
      exception.response()?.errorBody()?.toString()
    } catch (e: Exception) {
      NetworkErrorMessages.UNKNOWN_ERROR
    }
  }
  return withTimeout(10000L) {
    retryWhen { cause, attempt ->
      when {
        (cause is IOException && attempt < Utils.MAX_RETRIES) -> {
          delay(Utils.getBackoffDelay(attempt))
          true
        }
        else -> {
          false
        }
      }
    }.onStart { emit(ApiResult.Loading(isLoading = true)) }
      .onCompletion { emit(ApiResult.Loading(isLoading = false)) }
      .catch { exception ->
        when (exception) {
          is TimeoutCancellationException -> {
            emit(
              ApiResult.Error(
                exception,
                NetworkErrorMessages.NETWORK_ERROR_TIMEOUT,
                408
              )
            )
          }
          is IOException -> emit(ApiResult.Error(exception, NetworkErrorMessages.NETWORK_ERROR, 400))
          is HttpException -> {
            val code = exception.code()
            val errorMessage = convertErrorBody(exception) ?: NetworkErrorMessages.UNKNOWN_ERROR
            emit(ApiResult.Error(exception, errorMessage, code))
          }
          else -> emit(ApiResult.Error(exception, NetworkErrorMessages.UNKNOWN_ERROR))
        }
      }
      .flowOn(dispatcher)
  }
}

/**
 * Cancelling job if active
 */
fun Job?.cancelIfActive() {
  if (this?.isActive == true) {
    cancel()
  }
}

/**
 * **How to use it**
 * @code
private val mainScope = MainScope()
val btnClick: Button = findViewById(R.id.btn)
btnClick.clicks()
.onEach {
//DO STUFF ON CLICK
}
.launchIn(mainScope)
 */
@ExperimentalCoroutinesApi
fun View.clicks(): Flow<Unit> = callbackFlow {
  val listener = View.OnClickListener { safeOffer(Unit) }
  setOnClickListener(listener)
  awaitClose {
    setOnClickListener(null)
  }
}

/**
 * Flow conversion of edittext text changes
 * `````Usage``````
 *  val editText = EditText
 *  editText
 *    .textChanges()
 *    .collect { charSeq ->
 *
 *    }
 */
@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
  return callbackFlow<CharSequence?> {
    val listener = object : TextWatcher {
      override fun afterTextChanged(s: Editable?) = Unit
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        safeOffer(s)
      }
    }
    addTextChangedListener(listener)
    awaitClose { removeTextChangedListener(listener) }
  }.onStart { emit(text) }
}

/**
 * wrapper over the offer method
 */
@ExperimentalCoroutinesApi
fun <E> SendChannel<E>.safeOffer(value: E) = !isClosedForSend && try {
  offer(value)
} catch (t: Throwable) {
  // Ignore all
  false
}

/**
 * Use this flow operator to allow only certain Type to flow
 */
inline fun <reified T> Flow<Any>.ofType(): Flow<T> {
  return takeWhile {
    it is T
  }.map { it as T }
}

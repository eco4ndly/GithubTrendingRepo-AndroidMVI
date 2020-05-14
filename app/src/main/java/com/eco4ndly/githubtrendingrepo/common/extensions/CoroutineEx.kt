package com.eco4ndly.githubtrendingrepo.common.extensions

import android.view.View
import com.eco4ndly.githubtrendingrepo.common.Utils
import com.eco4ndly.githubtrendingrepo.data.api.ApiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.io.IOException

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
fun <T : Any> Flow<ApiResult<T>>.applyCommonStuffs() =
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
  }
      .onStart { emit(ApiResult.Loading(isLoading = true)) }
      .onCompletion { emit(ApiResult.Loading(isLoading = false)) }
      .catch { exception ->
          Timber.e(exception)
          emit(ApiResult.Error(exception))
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

@ExperimentalCoroutinesApi
fun <E> SendChannel<E>.safeOffer(value: E) = !isClosedForSend && try {
  offer(value)
} catch (t: Throwable) {
  // Ignore all
  false
}

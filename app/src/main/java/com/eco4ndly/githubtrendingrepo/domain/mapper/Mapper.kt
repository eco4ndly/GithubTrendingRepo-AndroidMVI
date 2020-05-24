package com.eco4ndly.githubtrendingrepo.domain.mapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A Sayan Porya code on 24/05/20
 */
interface Mapper<Output> {
  suspend fun map(): Output {
    return withContext(Dispatchers.Default) {
      getMapping()
    }
  }

  fun getMapping(): Output
}
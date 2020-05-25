package com.eco4ndly.githubtrendingrepo.domain.mapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A Sayan Porya code on 24/05/20
 *
 * Interface to be implemented data class mapper classes
 */
interface Mapper<Output> {

  /**
   * Maps using getMapping() function
   */
  suspend fun map(): Output {
    return withContext(Dispatchers.Default) {
      getMapping()
    }
  }

  /**
   * Gets the mapping of Input -> Output to use in [map()] function
   */
  fun getMapping(): Output
}
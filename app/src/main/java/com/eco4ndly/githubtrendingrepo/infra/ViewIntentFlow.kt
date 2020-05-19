package com.eco4ndly.githubtrendingrepo.infra

import kotlinx.coroutines.flow.Flow

/**
 * A Sayan Porya code on 18/05/20
 *
 * This allows us to group all view intents from one view to
 * a single flow
 */
interface ViewIntentFlow<Intent> {
  /**
   * Will return merged the intent flows
   */
  fun viewIntent(): Flow<Intent>
}
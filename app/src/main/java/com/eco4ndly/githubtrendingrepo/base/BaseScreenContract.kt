package com.eco4ndly.githubtrendingrepo.base

import kotlinx.coroutines.flow.Flow

/**
 * A Sayan Porya code on 02/06/20
 *
 * Interface each screen must implement.
 * This works as a contract between screen and the corresponding viewmodel.
 */
interface BaseScreenContract<ViewState, ViewEffect, Intent> {

  /**
   * shows the view effect from viewmodel
   */
  fun showViewEffect(viewEffect: ViewEffect)

  /**
   * renders state from viewmodel
   */
  fun renderViewState(viewState: ViewState)

  /**
   * Provides the flow of intents to the viewModel
   */
  fun viewIntent(): Flow<Intent>
}
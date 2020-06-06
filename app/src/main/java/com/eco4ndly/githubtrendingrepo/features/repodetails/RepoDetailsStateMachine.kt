package com.eco4ndly.githubtrendingrepo.features.repodetails

import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel

/**
 * A Sayan Porya code on 05/06/20
 */

/**
 * Intents of [RepoDetailsFragment]
 */
sealed class RepoDetailsIntent {
  /**
   * When fragment is loaded initially
   */
  data class InitialDataIntent(val repoUiModel: RepoUiModel): RepoDetailsIntent()
}

/**
 * View effects of [RepoDetailsFragment]
 */
sealed class RepoDetailsViewEffect

/**
 * State of [RepoDetailsFragment]
 */
data class RepoDetailsViewState(
  val repoUiModel: RepoUiModel?
) {
  companion object {
    val initial = RepoDetailsViewState(repoUiModel = null)
  }
}
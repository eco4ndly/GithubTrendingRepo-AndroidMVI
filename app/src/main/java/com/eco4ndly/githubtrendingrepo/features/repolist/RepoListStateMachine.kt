package com.eco4ndly.githubtrendingrepo.features.repolist

import com.eco4ndly.githubtrendingrepo.data.entities.RepoModel

/**
 * A Sayan Porya code on 21/05/20
 */

/**
 * States for [RepoListFragment]
 */
data class RepoListState(
  val repoList: List<RepoModel>,
  val hasError: Boolean,
  val message: String
) {
  companion object {
    val initial
      get() = RepoListState(
        repoList = emptyList(),
        hasError = false,
        message = "Fetching List..."
      )
  }
}

/**
 * Effects for [RepoListFragment]
 */
sealed class RepoListEffect

/**
 * Intents od [RepoListFragment]
 */
sealed class RepoListIntent {
  /**
   * Fetches the list of trending repository
   */
  object FetchTrendingRepo: RepoListIntent()
}
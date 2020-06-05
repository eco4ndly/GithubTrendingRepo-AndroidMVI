package com.eco4ndly.githubtrendingrepo.features.repolist

import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel

/**
 * A Sayan Porya code on 21/05/20
 */

/**
 * States for [RepoListFragment]
 */
data class RepoListState(
  val repoList: List<RepoUiModel>,
  val hasError: Boolean,
  val errorMessage: String?,
  val isLoadingList: Boolean
) {
  companion object {
    val initial
      get() = RepoListState(
        repoList = emptyList(),
        hasError = false,
        isLoadingList = true,
        errorMessage = null
      )
  }
}

/**
 * Effects for [RepoListFragment]
 */
sealed class RepoListEffect {
  sealed class NavigationEvent: RepoListEffect() {
    data class NavigateToDetailsScreen(val repoUiModel: RepoUiModel): NavigationEvent()
  }
}

/**
 * Intents od [RepoListFragment]
 */
sealed class RepoListIntent {
  /**
   * Fetches the list of trending repository
   */
  object FetchTrendingRepo: RepoListIntent()

  /**
   * When user clicks on repo item from the list
   */
  data class ListItemSelectionIntent(val item: RepoUiModel): RepoListIntent()

  data class ProfilePicClickIntent(val picUrl: String): RepoListIntent()
}
package com.eco4ndly.githubtrendingrepo.features.repodetails

import androidx.lifecycle.viewModelScope
import com.eco4ndly.githubtrendingrepo.base.BaseViewModel
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsIntent.OpenRepoInBrowserRequestIntent
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsViewEffect.OpenWebBrowser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@FlowPreview
class RepoDetailsViewModel(initialState: RepoDetailsViewState) :
  BaseViewModel<RepoDetailsViewState, RepoDetailsViewEffect, RepoDetailsIntent>(initialState) {

  init {
    viewIntent()
      .toState()
      .launchIn(viewModelScope)
  }

  private fun Flow<RepoDetailsIntent>.toState(): Flow<RepoDetailsIntent> {
    return onEach {
      when(it) {
        is RepoDetailsIntent.InitialDataIntent -> newState {
          copy(repoUiModel = it.repoUiModel)
        }
        is RepoDetailsIntent.BuiltByListItemClickIntent -> {
          dispatchViewEffect(RepoDetailsViewEffect.OpenWebBrowser(it.builtByUiModel.profileLink))
        }
        is OpenRepoInBrowserRequestIntent -> {
          viewState.value?.repoUiModel?.url?.let { url ->
            dispatchViewEffect(OpenWebBrowser(url))
          }
        }
      }
    }
  }

}
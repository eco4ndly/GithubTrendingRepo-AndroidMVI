package com.eco4ndly.githubtrendingrepo.features.repolist

import androidx.lifecycle.viewModelScope
import com.eco4ndly.githubtrendingrepo.base.BaseViewModel
import com.eco4ndly.githubtrendingrepo.data.api.ApiResult
import com.eco4ndly.githubtrendingrepo.domain.data.TrendingRepoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class RepoListViewModel(
  initialState: RepoListState,
  private val trendingRepoRepository: TrendingRepoRepository
) : BaseViewModel<RepoListState, RepoListEffect, RepoListIntent>(initialState) {


  init {
    viewModelScope.launch {
      viewIntent()
        .toState()
    }
  }

  private fun Flow<RepoListIntent>.toState(): Flow<RepoListIntent> {
    return onEach {
      when (it) {
        is RepoListIntent.FetchTrendingRepo -> {
          trendingRepoRepository.getTrendingRepositoryList()
            .collect { apiResult ->
              when (apiResult) {
                is ApiResult.Success -> newState {
                  copy(
                    hasError = false,
                    isLoadingList = false,
                    repoList = apiResult.data
                  )
                }
                is ApiResult.Error -> newState {
                  copy(
                    hasError = true,
                    isLoadingList = false,
                    errorMessage = apiResult.message
                  )
                }
                is ApiResult.Loading -> newState {
                  copy(
                    isLoadingList = true,
                    hasError = false
                  )
                }
              }
            }
        }
      }
    }
  }

}

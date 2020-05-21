package com.eco4ndly.githubtrendingrepo.features.repolist

import androidx.lifecycle.ViewModel
import com.eco4ndly.githubtrendingrepo.base.BaseViewModel
import com.eco4ndly.githubtrendingrepo.domain.TrendingRepoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class RepoListViewModel(
  initialState: RepoListState,
  private val trendingRepoRepository: TrendingRepoRepository
) : BaseViewModel<RepoListState, RepoListEffect, RepoListIntent>(initialState) {

}

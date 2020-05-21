package com.eco4ndly.githubtrendingrepo.features.repolist.di

import com.eco4ndly.githubtrendingrepo.data.repositories.TrendingRepoRepositoryImpl
import com.eco4ndly.githubtrendingrepo.domain.TrendingRepoRepository
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListState
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * A Sayan Porya code on 21/05/20
 */

@FlowPreview
@ExperimentalCoroutinesApi
val repoListFragmentModule = module {
  factory<TrendingRepoRepository> { TrendingRepoRepositoryImpl(get()) }
  viewModel { (initialState: RepoListState) -> RepoListViewModel(initialState, get()) }
}
package com.eco4ndly.githubtrendingrepo.features.repodetails.di

import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsViewModel
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsViewState
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * A Sayan Porya code on 05/06/20
 */

val repoDetailsModule = module {
  viewModel { (initialState: RepoDetailsViewState) -> RepoDetailsViewModel(initialState) }
}
package com.eco4ndly.githubtrendingrepo.features.repodetails

import com.eco4ndly.githubtrendingrepo.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class RepoDetailsViewModel(initialState: RepoDetailsViewState) :
  BaseViewModel<RepoDetailsViewState, RepoDetailsViewEffect, RepoDetailsIntent>(initialState) {

}
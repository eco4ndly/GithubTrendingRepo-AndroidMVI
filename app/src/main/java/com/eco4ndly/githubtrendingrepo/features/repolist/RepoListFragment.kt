package com.eco4ndly.githubtrendingrepo.features.repolist

import android.os.Bundle

import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class RepoListFragment : BaseFragment<RepoListState, RepoListEffect, RepoListIntent, RepoListViewModel>() {

  companion object {
    fun newInstance() = RepoListFragment()
  }

  override val viewModel: RepoListViewModel by viewModel { parametersOf(RepoListState.initial) }

  override fun renderViewState(viewState: RepoListState) {

  }

  override fun renderViewEffect(viewEffect: RepoListEffect) {

  }

  override fun layoutResId(): Int = R.layout.repo_list_fragment

  override fun takeOff(savedInstanceState: Bundle?) {

  }

}

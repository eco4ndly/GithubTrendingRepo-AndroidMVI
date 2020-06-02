package com.eco4ndly.githubtrendingrepo.features.repolist.ui

import android.os.Bundle

import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.base.BaseFragment
import com.eco4ndly.githubtrendingrepo.common.extensions.gone
import com.eco4ndly.githubtrendingrepo.common.extensions.setUpBasicList
import com.eco4ndly.githubtrendingrepo.common.extensions.showMessageDialog
import com.eco4ndly.githubtrendingrepo.common.extensions.visible
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListEffect
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListIntent
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListState
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListViewModel
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepoLisDiffCallback
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepositoryListItem
import com.eco4ndly.githubtrendingrepo.widgets.ItemAdapter
import kotlinx.android.synthetic.main.repo_list_fragment.pb_list_load
import kotlinx.android.synthetic.main.repo_list_fragment.rv_repo_list
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class RepoListFragment :
  BaseFragment<RepoListState, RepoListEffect, RepoListIntent, RepoListViewModel>() {

  private val repoListAdapter: ItemAdapter<RepositoryListItem, RepositoryListItem.Event> by lazy {
    ItemAdapter(
      RepoLisDiffCallback()
    )
  }

  companion object {
    const val TAG = "RepoListFragment"
    fun newInstance() =
      RepoListFragment()
  }

  override val viewModel: RepoListViewModel by viewModel { parametersOf(
    RepoListState.initial
  ) }

  override fun renderViewState(viewState: RepoListState) {

    if (viewState.isLoadingList) {
      pb_list_load.visible()
    } else {
      pb_list_load.gone()
    }

    if (viewState.hasError) {
      viewState.errorMessage?.let {
        showMessageDialog(it)
      }
      return
    }

    if (viewState.repoList.isNotEmpty()) {
      repoListAdapter.submitList(RepositoryListItem.from(viewState.repoList))
    }
  }

  override fun renderViewEffect(viewEffect: RepoListEffect) {

  }

  override fun layoutResId(): Int = R.layout.repo_list_fragment

  override fun takeOff(savedInstanceState: Bundle?) {
    pb_list_load.gone()
    rv_repo_list.setUpBasicList(repoListAdapter)
  }

}

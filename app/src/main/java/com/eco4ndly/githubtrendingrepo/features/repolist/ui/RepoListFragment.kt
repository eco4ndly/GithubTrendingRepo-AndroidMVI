package com.eco4ndly.githubtrendingrepo.features.repolist.ui

import android.os.Bundle

import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.base.BaseFragment
import com.eco4ndly.githubtrendingrepo.common.extensions.gone
import com.eco4ndly.githubtrendingrepo.common.extensions.safeOffer
import com.eco4ndly.githubtrendingrepo.common.extensions.setUpBasicList
import com.eco4ndly.githubtrendingrepo.common.extensions.showIf
import com.eco4ndly.githubtrendingrepo.common.extensions.showMessageDialog
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListEffect
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListEffect.NavigationEvent
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListEffect.NavigationEvent.NavigateToDetailsScreen
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListIntent
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListIntent.FetchTrendingRepo
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListIntent.ListItemSelectionIntent
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListIntent.ProfilePicClickIntent
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListState
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListViewModel
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepoLisDiffCallback
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepositoryListItem
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepositoryListItem.Event
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepositoryListItem.Event.ItemClicked
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepositoryListItem.Event.ProfilePicClick
import com.eco4ndly.githubtrendingrepo.widgets.ItemAdapter
import kotlinx.android.synthetic.main.repo_list_fragment.pb_list_load
import kotlinx.android.synthetic.main.repo_list_fragment.rv_repo_list
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class RepoListFragment :
  BaseFragment<RepoListState, RepoListEffect, RepoListIntent, RepoListViewModel>() {

  private val repoListAdapter: ItemAdapter<RepositoryListItem, Event> by lazy {
    ItemAdapter(
      RepoLisDiffCallback()
    )
  }

  private val repoListFetchIntent = ConflatedBroadcastChannel<Unit>()

  companion object {
    const val TAG = "RepoListFragment"
    fun newInstance() =
      RepoListFragment()
  }

  override val viewModel: RepoListViewModel by viewModel { parametersOf(RepoListState.initial) }

  override fun renderViewState(viewState: RepoListState) {

    pb_list_load.showIf { viewState.isLoadingList }

    if (viewState.hasError) {
      viewState.errorMessage?.let {
        showMessageDialog(it)
      }
      return
    }

    if (viewState.repoList.isNotEmpty()) {
      repoListAdapter.submitList(RepositoryListItem.mapAsListItem(viewState.repoList))
    }
  }

  override fun showViewEffect(viewEffect: RepoListEffect) {
    when(viewEffect) {
      is NavigationEvent -> {
        when(viewEffect) {
          is NavigateToDetailsScreen -> {
            //TODO - Navigate to Details Screen
          }
        }
      }
    }
  }

  override fun layoutResId(): Int = R.layout.repo_list_fragment

  override fun takeOff(savedInstanceState: Bundle?) {
    pb_list_load.gone()
    rv_repo_list.setUpBasicList(repoListAdapter)
    repoListFetchIntent.safeOffer(Unit)
  }

  override fun viewIntent(): Flow<RepoListIntent> {
    val intents = listOf(
      repoListFetchIntent.asFlow().map { FetchTrendingRepo },
      repoListAdapter.eventFlow.toIntent()
    )
    return intents.asFlow().flattenMerge(intents.size)
  }

  private fun Flow<Event>.toIntent(): Flow<RepoListIntent> {
    return map {
      when(it) {
        is ItemClicked -> ListItemSelectionIntent(it.repoUiModel)
        is ProfilePicClick -> ProfilePicClickIntent(it.picUrl)
      }
    }
  }

}

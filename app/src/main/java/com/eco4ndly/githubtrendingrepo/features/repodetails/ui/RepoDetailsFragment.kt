package com.eco4ndly.githubtrendingrepo.features.repodetails.ui

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.base.BaseFragment
import com.eco4ndly.githubtrendingrepo.common.extensions.clicks
import com.eco4ndly.githubtrendingrepo.common.extensions.ifUrlOpenBrowserElse
import com.eco4ndly.githubtrendingrepo.common.extensions.safeOffer
import com.eco4ndly.githubtrendingrepo.common.extensions.setUpBasicList
import com.eco4ndly.githubtrendingrepo.features.repodetails.BuiltByListDiffUtilCallback
import com.eco4ndly.githubtrendingrepo.features.repodetails.BuiltByListItem
import com.eco4ndly.githubtrendingrepo.features.repodetails.Event
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsIntent
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsIntent.BuiltByListItemClickIntent
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsIntent.InitialDataIntent
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsIntent.OpenRepoInBrowserRequestIntent
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsViewEffect
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsViewEffect.OpenWebBrowser
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsViewModel
import com.eco4ndly.githubtrendingrepo.features.repodetails.RepoDetailsViewState
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel
import com.eco4ndly.githubtrendingrepo.infra.event.argument
import com.eco4ndly.githubtrendingrepo.widgets.ItemAdapter
import kotlinx.android.synthetic.main.repo_details_fragment.iv_open_repo_in_browser
import kotlinx.android.synthetic.main.repo_details_fragment.rv_built_by
import kotlinx.android.synthetic.main.repo_details_fragment.tv_repo_details
import kotlinx.android.synthetic.main.repo_details_fragment.tv_repo_name
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class RepoDetailsFragment : BaseFragment<RepoDetailsViewState, RepoDetailsViewEffect, RepoDetailsIntent, RepoDetailsViewModel>() {

  private val initialDataIntent = ConflatedBroadcastChannel<RepoUiModel>()
  private var mRepoDetails: RepoUiModel by argument()

  private val mBuiltByListAdapter: ItemAdapter<BuiltByListItem, Event> by lazy {
    ItemAdapter(BuiltByListDiffUtilCallback())
  }

  companion object {
    const val TAG = "RepoDetailsFragment"
    fun newInstance(repoDetails: RepoUiModel): RepoDetailsFragment {
      return RepoDetailsFragment()
        .apply {
        mRepoDetails = repoDetails
      }
    }
  }

  override val viewModel: RepoDetailsViewModel by viewModel { parametersOf(
    RepoDetailsViewState.initial
  ) }

  override fun layoutResId(): Int = R.layout.repo_details_fragment

  override fun takeOff(savedInstanceState: Bundle?) {
    initialDataIntent.safeOffer(mRepoDetails)
  }

  override fun showViewEffect(viewEffect: RepoDetailsViewEffect) {
    when(viewEffect) {
      is OpenWebBrowser -> {
        viewEffect.url.ifUrlOpenBrowserElse(context) {
          Toast.makeText( context, "Not an valid url", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  override fun renderViewState(viewState: RepoDetailsViewState) {
    viewState.repoUiModel?.let {
      tv_repo_name.text = it.repoName
      tv_repo_details.text = it.description
      rv_built_by.apply {
        adapter = mBuiltByListAdapter
        layoutManager = GridLayoutManager(context, 2)
        setHasFixedSize(true)
      }

      mBuiltByListAdapter.submitList(BuiltByListItem.mapAsListItem(it.builtBy))
    }
  }

  override fun viewIntent(): Flow<RepoDetailsIntent> {
    val intents = listOf(
      initialDataIntent.asFlow().map { InitialDataIntent(it) },
      mBuiltByListAdapter.eventFlow.toIntent(),
      iv_open_repo_in_browser.clicks().map { OpenRepoInBrowserRequestIntent }
    )

    return intents.asFlow().flattenMerge(intents.size)
  }

  private fun Flow<Event>.toIntent(): Flow<RepoDetailsIntent> {
    return map {
      when(it) {
        is Event.ItemClicked -> BuiltByListItemClickIntent(it.builtBy)
      }
    }
  }

}
package com.eco4ndly.githubtrendingrepo.features.repodetails

import android.os.Bundle
import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.base.BaseFragment
import com.eco4ndly.githubtrendingrepo.common.extensions.safeOffer
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel
import com.eco4ndly.githubtrendingrepo.infra.event.argument
import kotlinx.android.synthetic.main.repo_details_fragment.text
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
class RepoDetailsFragment : BaseFragment<RepoDetailsViewState, RepoDetailsViewEffect, RepoDetailsIntent, RepoDetailsViewModel>() {

  private val initialDataIntent = ConflatedBroadcastChannel<RepoUiModel>()
  private var mRepoDetails: RepoUiModel by argument()

  companion object {
    const val TAG = "RepoDetailsFragment"
    fun newInstance(repoDetails: RepoUiModel): RepoDetailsFragment {
      return RepoDetailsFragment().apply {
        mRepoDetails = repoDetails
      }
    }
  }

  override val viewModel: RepoDetailsViewModel by viewModel { parametersOf(RepoDetailsViewState.initial) }

  override fun layoutResId(): Int = R.layout.repo_details_fragment

  override fun takeOff(savedInstanceState: Bundle?) {
    initialDataIntent.safeOffer(mRepoDetails)

    text.text = mRepoDetails.description
  }

  override fun showViewEffect(viewEffect: RepoDetailsViewEffect) {

  }

  override fun renderViewState(viewState: RepoDetailsViewState) {

  }

  override fun viewIntent(): Flow<RepoDetailsIntent> {
    val intents = listOf(initialDataIntent.asFlow().map { RepoDetailsIntent.InitialDataIntent(it) })

    return intents.asFlow().flattenMerge(intents.size)
  }

}
package com.eco4ndly.githubtrendingrepo.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * A Sayan Porya code on 13/05/20
 *
 * Fragments should be created by extending this class
 *
 * @see [BaseViewModel] to know about [ViewState] [ViewEffect] [Intent]
 */
abstract class BaseFragment<ViewState, ViewEffect, Intent, AppViewModel : BaseViewModel<ViewState, ViewEffect, Intent>> :
    Fragment() {

  /**
   * The Viewmodel
   */
  abstract val viewModel: AppViewModel

  @ExperimentalCoroutinesApi
  @FlowPreview
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    lifecycleScope.launchWhenStarted {
      viewModel.getViewStateLiveData().observe(viewLifecycleOwner, viewStateObserver)
    }
    lifecycleScope.launchWhenStarted {
      viewModel.viewEffectFlow.onEach {
        renderViewEffect(it)
      }.collect()
    }
  }

  private val viewStateObserver = Observer<ViewState> {
    Timber.d("current state $it")
    renderViewState(it)
  }

  private val viewEffectObserver = Observer<ViewEffect> {
    Timber.d("current effect $it")
    renderViewEffect(it)
  }

  /**
   * Sub classes will implement this method to render view states
   */
  abstract fun renderViewState(viewState: ViewState)

  /**
   * Sub classes will implement this method to render view effects
   */
  abstract fun renderViewEffect(viewEffect: ViewEffect)
}
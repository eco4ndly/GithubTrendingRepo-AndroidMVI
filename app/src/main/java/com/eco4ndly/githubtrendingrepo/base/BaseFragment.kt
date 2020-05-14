package com.eco4ndly.githubtrendingrepo.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.getViewStateLiveData().observe(viewLifecycleOwner, viewStateObserver)
    viewModel.getViewEffectLiveDate().observe(viewLifecycleOwner, viewEffectObserver)
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
package com.eco4ndly.githubtrendingrepo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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
@ExperimentalCoroutinesApi
@FlowPreview
abstract class BaseFragment<ViewState, ViewEffect, Intent, AppViewModel : BaseViewModel<ViewState, ViewEffect, Intent>> :
    Fragment(), BaseScreenContract<ViewState, ViewEffect, Intent> {

  /**
   * The Viewmodel
   */
  abstract val viewModel: AppViewModel
  
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    lifecycleScope.launchWhenStarted {
      viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }
    lifecycleScope.launchWhenStarted {
      viewModel.viewEffect.onEach { showViewEffect(it) }.collect()
    }

    viewIntent()
      .onEach { viewModel.processIntent(it) }.launchIn(lifecycleScope)

    takeOff(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layoutResId(), container, false)
  }


  private val viewStateObserver = Observer<ViewState> {
    Timber.d("current state $it")
    renderViewState(it)
  }

  private val viewEffectObserver = Observer<ViewEffect> {
    Timber.d("current effect $it")
    showViewEffect(it)
  }

  /**
   * gets the layout res from its child to build the view in [onCreateView]
   */
  @LayoutRes
  abstract fun layoutResId(): Int

  /**
   *from where children should start
   */
  abstract fun takeOff(savedInstanceState: Bundle?)
}
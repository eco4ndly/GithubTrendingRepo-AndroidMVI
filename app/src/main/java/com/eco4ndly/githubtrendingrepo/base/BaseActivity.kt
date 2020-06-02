package com.eco4ndly.githubtrendingrepo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.eco4ndly.githubtrendingrepo.infra.event.EventObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * A Sayan Porya code on 13/05/20
 *
 * Activity should be created by extending this class
 *
 * @see [BaseViewModel] to know about [ViewState] [ViewEffect] [Intent]
 */
abstract class BaseActivity<ViewState, ViewEffect, Intent, AppViewModel : BaseViewModel<ViewState, ViewEffect, Intent>> :
  AppCompatActivity(), BaseScreenContract<ViewState, ViewEffect, Intent> {

  /**
   * The viewmodel instance
   */
  abstract val viewModel: AppViewModel

  @ExperimentalCoroutinesApi
  @FlowPreview
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycleScope.launchWhenStarted {
      viewModel.viewState.observe(this@BaseActivity, viewStateObserver)
    }
    lifecycleScope.launchWhenStarted {
      viewModel.viewEffect
        .onEach { effect ->
          showViewEffect(effect)
        }.collect()
    }
  }

  private val viewStateObserver = Observer<ViewState> {
    Timber.d("current state $it")
    renderViewState(it)
  }

  private val viewEffectObserver = EventObserver<ViewEffect> {
    Timber.d("current effect $it")
    showViewEffect(it)
  }
}
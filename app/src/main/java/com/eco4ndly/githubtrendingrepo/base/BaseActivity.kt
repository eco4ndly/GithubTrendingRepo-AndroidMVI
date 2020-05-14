package com.eco4ndly.githubtrendingrepo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.eco4ndly.githubtrendingrepo.infra.event.EventObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A Sayan Porya code on 13/05/20
 *
 * Activity should be created by extending this class
 *
 * @see [BaseViewModel] to know about [ViewState] [ViewEffect] [Intent]
 */
abstract class BaseActivity<ViewState, ViewEffect, Intent, AppViewModel : BaseViewModel<ViewState, ViewEffect, Intent>> :
    AppCompatActivity() {

  /**
   * The viewmodel instance
   */
  abstract val viewModel: AppViewModel

  protected val mainScope get() = MainScope()

  @ExperimentalCoroutinesApi
  @FlowPreview
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.getViewStateLiveData().observe(this, viewStateObserver)
    //viewModel.getViewEffectLiveDate().observe(this, viewEffectObserver)
    mainScope.launch {
      viewModel.viewEffectFlow.collect { effect ->
        renderViewEffect(effect)
      }
    }
  }

  private val viewStateObserver = Observer<ViewState> {
    Timber.d("current state $it")
    renderViewState(it)
  }

  private val viewEffectObserver = EventObserver<ViewEffect> {
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

  override fun onDestroy() {
    super.onDestroy()
    mainScope.cancel()
  }
}
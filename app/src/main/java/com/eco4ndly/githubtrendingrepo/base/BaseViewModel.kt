package com.eco4ndly.githubtrendingrepo.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eco4ndly.githubtrendingrepo.common.extensions.safeOffer
import com.eco4ndly.githubtrendingrepo.infra.event.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import timber.log.Timber

/**
 * A Sayan Porya code on 13/05/20
 *
 * This class should be extended while creating viewmodels
 *
 * @param ViewState this will be used to represent the state of the corresponding view at one
 * point of time. ViewState will be immutable by nature so that the state is always maintained same and
 * can't be modified on fly
 *
 * @param ViewEffect It represents events like toast, snack bar, navigation etc
 *
 * @param Intent This represents user actions from view to viewmodel
 */
@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<ViewState, ViewEffect, Intent>(initialState: ViewState) : ViewModel() {

  private val viewStateMutableLD = MutableLiveData<ViewState>()

  private val stateReducer = BroadcastChannel<ViewState.() -> ViewState>(capacity = Channel.BUFFERED)
  private val effectDispatcher = BroadcastChannel<ViewEffect>(capacity = Channel.BUFFERED)

  val viewEffect: Flow<ViewEffect> get() = effectDispatcher.asFlow()

  /**
   * Exposes the [ViewState] live data
   */
  val viewState: LiveData<ViewState> get() = viewStateMutableLD

  init {
    stateReducer
      .asFlow()
      .scan(initialState) { vs, reducer -> reducer(vs) }
      .onEach { viewStateMutableLD.value = it }
      .catch { Timber.d(it) }
      .launchIn(viewModelScope)
  }

  /**
   * use this function to reduce a new state
   */
  protected fun newState(reducer: ViewState.() -> ViewState) = stateReducer.safeOffer(reducer)

  /**
   * Dispatches view effect to the view
   */
  protected fun dispatchViewEffect(viewEffect: ViewEffect) {
    Timber.d("setting viewEffect : $viewEffect")
    effectDispatcher.safeOffer(viewEffect)
  }

  /**
   * Place where we'll be handling user actions as intents
   */
  abstract fun processIntent(intent: Intent)

  override fun onCleared() {
    super.onCleared()
    effectDispatcher.cancel()
    stateReducer.cancel()
  }

  protected fun <T: Intent>Flow<T>.logIntent(): Flow<T> {
    return onEach {
      Timber.d("#Commanding Intent $it")
    }
  }
}
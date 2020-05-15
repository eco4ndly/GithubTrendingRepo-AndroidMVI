package com.eco4ndly.githubtrendingrepo.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eco4ndly.githubtrendingrepo.common.extensions.safeOffer
import com.eco4ndly.githubtrendingrepo.infra.event.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber
import java.lang.RuntimeException

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
abstract class BaseViewModel<ViewState, ViewEffect, Intent> : ViewModel() {

  private val viewStateMutableLD = MutableLiveData<ViewState>()
  private val _viewEffectChannel = BroadcastChannel<ViewEffect>(capacity = Channel.BUFFERED)

  /**
   * Exposes the [ViewState] live data
   */
  val viewState: LiveData<ViewState> get() = viewStateMutableLD

  /**
   * Current View state
   */
  protected var viewStateStore: ViewState? = null
    get() = field ?: throw RuntimeException("Trying to access View State before initialization")
    set(value) {
      value?.let {
        Timber.d("Setting ViewState: $value")
        field = value
        viewStateMutableLD.value = value
      }
    }

  /**
   * Dispatches view effect to the view
   */
  protected fun dispatchViewEffect(viewEffect: ViewEffect) {
    Timber.d("setting viewEffect : $viewEffect")
    _viewEffectChannel.safeOffer(viewEffect) //to flow
  }

  val viewEffect: Flow<ViewEffect> get() = _viewEffectChannel.asFlow()

  /**
   * Place where we'll be handling user actions as intents
   */
  abstract fun processIntent(intent: Intent)

  override fun onCleared() {
    super.onCleared()
    _viewEffectChannel.cancel()
  }
}
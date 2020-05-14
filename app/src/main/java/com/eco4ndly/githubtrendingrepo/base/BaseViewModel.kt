package com.eco4ndly.githubtrendingrepo.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
import kotlinx.coroutines.channels.Channel.Factory
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
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
abstract class BaseViewModel<ViewState, ViewEffect, Intent> : ViewModel() {

  private val viewStateMutableLD = MutableLiveData<ViewState>()

  /**
   * Exposes the [ViewState] live data
   */
  fun getViewStateLiveData(): LiveData<ViewState> = viewStateMutableLD

  /**
   * Current View state
   */
  protected var viewState: ViewState? = null
    get() = field ?: throw RuntimeException("Trying to access View State before initialization")
    set(value) {
      value?.let {
        Timber.d("Setting ViewState: $value")
        field = value
        viewStateMutableLD.value = value
      }
    }

  private val viewEffectSingleLiveEvent = MutableLiveData<Event<ViewEffect>>()

  /**
   * Exposes the View Effect live data
   */
  fun getViewEffectLiveDate(): LiveData<Event<ViewEffect>> = viewEffectSingleLiveEvent

  /**
   * Current View Effect
   *//*
  @FlowPreview
  @ExperimentalCoroutinesApi
  protected var viewEffect: ViewEffect? = null
    get() = field ?: throw RuntimeException("Trying to access View Effects before initialization")
    set(value) {
      value?.let {
        Timber.d("setting viewEffect : $value")
        field = value
        //viewEffectSingleLiveEvent.value = Event(value) //to the live data
        viewEffectChannel.safeOffer(it) //to flow
      }
    }*/
  /**
   * Dispatches view effect to the view
   */
  protected fun dispatchViewEffect(viewEffect: ViewEffect) {
    Timber.d("setting viewEffect : $viewEffect")
    //viewEffectSingleLiveEvent.value = Event(value) //to the live data
    viewEffectChannel.safeOffer(viewEffect) //to flow
  }

  @ExperimentalCoroutinesApi
  private val viewEffectChannel = ConflatedBroadcastChannel<ViewEffect>()

  @FlowPreview
  @ExperimentalCoroutinesApi
  val viewEffectFlow: Flow<ViewEffect> get() = viewEffectChannel.asFlow()

  /**
   * Place where we'll be handling user actions as intents
   */
  abstract fun processIntent(intent: Intent)

  override fun onCleared() {
    super.onCleared()
    viewEffectChannel.cancel()
  }
}
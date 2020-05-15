package com.eco4ndly.githubtrendingrepo.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.eco4ndly.githubtrendingrepo.base.BaseViewModel
import com.eco4ndly.githubtrendingrepo.common.extensions.safeOffer
import com.eco4ndly.githubtrendingrepo.domain.TrendingRepoRepository
import com.eco4ndly.githubtrendingrepo.main.MainEffect
import com.eco4ndly.githubtrendingrepo.main.MainEffect.ToastEffect
import com.eco4ndly.githubtrendingrepo.main.MainIntent
import com.eco4ndly.githubtrendingrepo.main.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * A Sayan Porya code on 14/05/20
 */
@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(
  initialState: MainState,
  trendingRepoRepository: TrendingRepoRepository
) : BaseViewModel<MainState, MainEffect, MainIntent>(initialState) {

  private val _intentChannel = ConflatedBroadcastChannel<MainIntent>()

  init {
    _intentChannel
      .asFlow()
      .logIntent()
      .toMainState()
      .launchIn(viewModelScope)
  }

  override fun processIntent(intent: MainIntent) {
    _intentChannel.safeOffer(intent)
  }


  private fun Flow<MainIntent>.toMainState(): Flow<MainIntent> {
    return onEach {
      when(it) {
        is MainIntent.CounterIntent -> {
          newState { copy(theText = "Count ${it.count}") }
          dispatchViewEffect(ToastEffect("Clicked"))
        }
      }
    }
  }
}
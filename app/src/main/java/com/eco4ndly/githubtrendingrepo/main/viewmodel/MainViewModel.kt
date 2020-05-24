package com.eco4ndly.githubtrendingrepo.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.eco4ndly.githubtrendingrepo.base.BaseViewModel
import com.eco4ndly.githubtrendingrepo.main.MainEffect
import com.eco4ndly.githubtrendingrepo.main.MainEffect.ToastEffect
import com.eco4ndly.githubtrendingrepo.main.MainIntent
import com.eco4ndly.githubtrendingrepo.main.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A Sayan Porya code on 14/05/20
 */
@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(
  initialState: MainState
) : BaseViewModel<MainState, MainEffect, MainIntent>(initialState) {

  init {
    viewIntent()
      .toMainState()
      .launchIn(viewModelScope)
  }

  private fun Flow<MainIntent>.toMainState(): Flow<MainIntent> {
    return onEach {
      when(it) {
        is MainIntent.ClickCountIntent -> {
          newState { copy(theText = "Count ${it.count}") }
          dispatchViewEffect(ToastEffect("Clicked"))
        }
        is MainIntent.CharCountIntent -> {
          newState { copy(charCount = it.text.length) }
        }
      }
    }
  }
}
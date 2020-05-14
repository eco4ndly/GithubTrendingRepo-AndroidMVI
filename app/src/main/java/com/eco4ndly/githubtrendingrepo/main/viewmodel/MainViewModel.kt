package com.eco4ndly.githubtrendingrepo.main.viewmodel

import com.eco4ndly.githubtrendingrepo.base.BaseViewModel
import com.eco4ndly.githubtrendingrepo.domain.TrendingRepoRepository
import com.eco4ndly.githubtrendingrepo.main.MainEffect
import com.eco4ndly.githubtrendingrepo.main.MainEffect.ToastEffect
import com.eco4ndly.githubtrendingrepo.main.MainIntent
import com.eco4ndly.githubtrendingrepo.main.MainState

/**
 * A Sayan Porya code on 14/05/20
 */
class MainViewModel(
  trendingRepoRepository: TrendingRepoRepository
) : BaseViewModel<MainState, MainEffect, MainIntent>() {

  override fun processIntent(intent: MainIntent) {
    dispatchViewEffect(ToastEffect("Test Flight"))
  }
}
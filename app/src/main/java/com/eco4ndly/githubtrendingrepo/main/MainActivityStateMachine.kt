package com.eco4ndly.githubtrendingrepo.main

/**
 * A Sayan Porya code on 14/05/20
 */

data class MainState (
  val theText: String
) {
  companion object {
    fun initial() = MainState(theText = "Initial")
  }
}

/**
 * Effects for main activity
 */
sealed class MainEffect {
  /**
   * To show toast
   */
  data class ToastEffect(val message: String) : MainEffect()
}

/**
 * Intents from [com.eco4ndly.githubtrendingrepo.main.ui.MainActivity] to
 * [com.eco4ndly.githubtrendingrepo.main.viewmodel.MainViewModel]
 */
sealed class MainIntent {
  /**
   * Test Intent
   */
  object ShowToastIntent: MainIntent()

  data class CounterIntent(val count: Int): MainIntent()
}
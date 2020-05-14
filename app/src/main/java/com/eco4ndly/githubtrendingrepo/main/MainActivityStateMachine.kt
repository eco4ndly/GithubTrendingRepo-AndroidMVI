package com.eco4ndly.githubtrendingrepo.main

/**
 * A Sayan Porya code on 14/05/20
 */

class MainState

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
}
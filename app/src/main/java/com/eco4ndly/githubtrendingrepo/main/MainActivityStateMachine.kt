package com.eco4ndly.githubtrendingrepo.main

/**
 * A Sayan Porya code on 14/05/20
 */

data class MainState (
  val theText: String,
  val charCount: Int
) {
  companion object {
    /**
     * The initial state of the [.MainActivity]
     */
    fun initial() = MainState(theText = "Click Here", charCount = 0)
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

  /**
   * Navigator
   */
  sealed class NavigationEffect: MainEffect() {
    /**
     * To navigate to the next activity
     */
    object NavigateListActivity: NavigationEffect()
  }
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

  /**
   * TextView Click count Intent
   */
  data class ClickCountIntent(val count: Int): MainIntent()

  /**
   * Intent to count the character present in the typed sentence
   */
  data class CharCountIntent(val text: String): MainIntent()

  sealed class NavigationIntent: MainIntent() {
    object ToListActivity: NavigationIntent()
  }
}
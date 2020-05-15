package com.eco4ndly.githubtrendingrepo.main.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.eco4ndly.githubtrendingrepo.base.BaseActivity
import com.eco4ndly.githubtrendingrepo.common.extensions.clicks
import com.eco4ndly.githubtrendingrepo.common.extensions.exhaustive
import com.eco4ndly.githubtrendingrepo.databinding.ActivityMainBinding
import com.eco4ndly.githubtrendingrepo.main.MainEffect
import com.eco4ndly.githubtrendingrepo.main.MainEffect.ToastEffect
import com.eco4ndly.githubtrendingrepo.main.MainIntent
import com.eco4ndly.githubtrendingrepo.main.MainIntent.ShowToastIntent
import com.eco4ndly.githubtrendingrepo.main.MainState
import com.eco4ndly.githubtrendingrepo.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A Sayann Porya code on 11/05/2020
 */
@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<MainState, MainEffect, MainIntent, MainViewModel>() {

  private val mab: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  override val viewModel: MainViewModel by viewModel { parametersOf(MainState.initial()) }

  private var counterVal = 0

  @ExperimentalCoroutinesApi
  @FlowPreview
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(mab.root)

    intents().onEach {
      viewModel.processIntent(it)
    }.launchIn(lifecycleScope)
  }

  override fun renderViewState(viewState: MainState) {
    mab.testTxt.text = viewState.theText
  }

  override fun renderViewEffect(viewEffect: MainEffect) {
    when (viewEffect) {
      is ToastEffect -> {
        Toast.makeText(this, viewEffect.message, Toast.LENGTH_SHORT).show()
      }
    }.exhaustive
  }

  private fun intents() = merge(
    mab.testTxt.clicks().map { MainIntent.CounterIntent(counterVal++) }
  )
}

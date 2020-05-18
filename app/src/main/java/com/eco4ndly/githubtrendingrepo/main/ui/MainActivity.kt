package com.eco4ndly.githubtrendingrepo.main.ui

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.lifecycleScope
import com.eco4ndly.githubtrendingrepo.base.BaseActivity
import com.eco4ndly.githubtrendingrepo.common.extensions.clicks
import com.eco4ndly.githubtrendingrepo.common.extensions.exhaustive
import com.eco4ndly.githubtrendingrepo.common.extensions.textChanges
import com.eco4ndly.githubtrendingrepo.common.extensions.toast
import com.eco4ndly.githubtrendingrepo.databinding.ActivityMainBinding
import com.eco4ndly.githubtrendingrepo.infra.ViewIntentFlow
import com.eco4ndly.githubtrendingrepo.main.MainEffect
import com.eco4ndly.githubtrendingrepo.main.MainEffect.ToastEffect
import com.eco4ndly.githubtrendingrepo.main.MainIntent
import com.eco4ndly.githubtrendingrepo.main.MainState
import com.eco4ndly.githubtrendingrepo.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A Sayann Porya code on 11/05/2020
 */
@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<MainState, MainEffect, MainIntent, MainViewModel>(), ViewIntentFlow<MainIntent> {

  private val mab: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  override val viewModel: MainViewModel by viewModel { parametersOf(MainState.initial()) }

  @VisibleForTesting
  private var counterVal = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(mab.root)

    viewIntent().onEach {
      viewModel.processIntent(it)
    }.launchIn(lifecycleScope)
  }

  override fun renderViewState(viewState: MainState) {
    mab.testTxt.text = viewState.theText
    mab.tvCharCount.text = viewState.charCount.toString()
  }

  override fun renderViewEffect(viewEffect: MainEffect) {
    when (viewEffect) {
      is ToastEffect -> {
        toast(viewEffect.message)
      }
    }.exhaustive
  }

  override fun viewIntent(): Flow<MainIntent> {
    val intents = listOf(
      mab.testTxt.clicks().map { MainIntent.ClickCountIntent(counterVal++) },
      mab.etText.textChanges().debounce(300).map { MainIntent.CharCountIntent(it?.toString()?:"") }
    )

    return intents.asFlow().flattenMerge(intents.size)
  }
}

package com.eco4ndly.githubtrendingrepo.main.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.lifecycleScope
import com.eco4ndly.githubtrendingrepo.base.BaseActivity
import com.eco4ndly.githubtrendingrepo.base.FragmentContainerActivity
import com.eco4ndly.githubtrendingrepo.common.extensions.clicks
import com.eco4ndly.githubtrendingrepo.common.extensions.exhaustive
import com.eco4ndly.githubtrendingrepo.common.extensions.ofType
import com.eco4ndly.githubtrendingrepo.common.extensions.textChanges
import com.eco4ndly.githubtrendingrepo.common.extensions.toast
import com.eco4ndly.githubtrendingrepo.databinding.ActivityMainBinding
import com.eco4ndly.githubtrendingrepo.main.MainEffect
import com.eco4ndly.githubtrendingrepo.main.MainEffect.NavigationEffect
import com.eco4ndly.githubtrendingrepo.main.MainEffect.ToastEffect
import com.eco4ndly.githubtrendingrepo.main.MainIntent
import com.eco4ndly.githubtrendingrepo.main.MainIntent.NavigationIntent
import com.eco4ndly.githubtrendingrepo.main.MainState
import com.eco4ndly.githubtrendingrepo.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

/**
 * A Sayann Porya code on 11/05/2020
 */
@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<MainState, MainEffect, MainIntent, MainViewModel>() {

  private val mab: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  override val viewModel: MainViewModel by viewModel { parametersOf(MainState.initial()) }

  private val channel = ConflatedBroadcastChannel<Any>()

  @VisibleForTesting
  private var counterVal = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(mab.root)

    channel
      .asFlow()
      .ofType<ActionAccept>()
      .onEach {
        Timber.d("Accepted $it")
      }.launchIn(lifecycleScope)

    mab.accept.setOnClickListener {
      channel.offer(ActionAccept())
    }

    mab.reject.setOnClickListener {
      channel.offer(ActionReject())
    }
  }

  override fun renderViewState(viewState: MainState) {
    mab.testTxt.text = viewState.theText
    mab.tvCharCount.text = viewState.charCount.toString()
  }

  override fun showViewEffect(viewEffect: MainEffect) {
    when (viewEffect) {
      is ToastEffect -> toast(viewEffect.message)
      is NavigationEffect -> handleNavigation(viewEffect)
    }
  }

  override fun viewIntent(): Flow<MainIntent> {
    val intents = listOf(
      mab.testTxt.clicks().map { MainIntent.ClickCountIntent(counterVal++) },
      mab.etText.textChanges().debounce(300)
        .map { MainIntent.CharCountIntent(it?.toString() ?: "") },
      mab.btnListActivity.clicks().map { MainIntent.NavigationIntent.ToListActivity }
    )

    return intents.asFlow().flattenMerge(intents.size)
  }

  private fun handleNavigation(navigationEffect: NavigationEffect) {
    when (navigationEffect) {
      is NavigationEffect.NavigateListActivity -> {
        Intent(this, FragmentContainerActivity::class.java).apply {
          startActivity(this)
        }
      }
    }
  }
}

class ActionAccept
class ActionReject

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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A Sayann Porya code on 11/05/2020
 */
class MainActivity : BaseActivity<MainState, MainEffect, MainIntent, MainViewModel>() {

  private lateinit var mab: ActivityMainBinding

  override val viewModel: MainViewModel by viewModel()

  @ExperimentalCoroutinesApi
  @FlowPreview
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mab = ActivityMainBinding.inflate(layoutInflater)
    setContentView(mab.root)

    mab.testTxt.clicks().onEach {
      viewModel.processIntent(ShowToastIntent)
    }.launchIn(lifecycleScope)
  }

  override fun renderViewState(viewState: MainState) {

  }

  override fun renderViewEffect(viewEffect: MainEffect) {
    when(viewEffect) {
      is ToastEffect -> {
        Toast.makeText(this, viewEffect.message, Toast.LENGTH_SHORT).show()
      }
    }.exhaustive
  }
}

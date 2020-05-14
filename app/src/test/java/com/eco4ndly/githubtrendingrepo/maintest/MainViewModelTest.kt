package com.eco4ndly.githubtrendingrepo.maintest

import com.eco4ndly.githubtrendingrepo.di.modules.networkModule
import com.eco4ndly.githubtrendingrepo.main.module.mainActivityModule
import com.eco4ndly.githubtrendingrepo.main.viewmodel.MainViewModel
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

/**
 * A Sayan Porya code on 14/05/20
 */
class MainViewModelTest: AutoCloseKoinTest() {

  private val mainViewModel: MainViewModel by inject()

  @Before
  fun before() {
    startKoin {
      modules(listOf(networkModule, mainActivityModule))
    }
  }

  @Test
  fun `main viewmodel should not be empty`() {
    assertNotNull(mainViewModel)
  }

  @After
  fun after() {
    stopKoin()
  }
}
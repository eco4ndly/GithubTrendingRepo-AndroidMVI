package com.eco4ndly.githubtrendingrepo

import com.eco4ndly.githubtrendingrepo.di.modules.networkModule
import com.eco4ndly.githubtrendingrepo.main.module.mainActivityModule
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.core.logger.Level
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

/**
 * A Sayan Porya code on 13/05/20
 */
class KoinModuleTest: AutoCloseKoinTest() {

    @Test
    fun `koin module test`() {
        koinApplication {
            printLogger(Level.DEBUG)
            modules(listOf(networkModule, mainActivityModule))
        }.checkModules()
    }
}
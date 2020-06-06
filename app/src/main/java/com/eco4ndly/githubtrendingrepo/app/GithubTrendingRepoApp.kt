package com.eco4ndly.githubtrendingrepo.app

import android.app.Application
import com.eco4ndly.githubtrendingrepo.di.modules.networkModule
import com.eco4ndly.githubtrendingrepo.features.repodetails.di.repoDetailsModule
import com.eco4ndly.githubtrendingrepo.features.repolist.di.repoListFragmentModule
import com.eco4ndly.githubtrendingrepo.main.module.mainActivityModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber

/**
 * A Sayan Porya code on 12/05/20
 */

class GithubTrendingRepoApp: Application() {
    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GithubTrendingRepoApp)
            modules(listOf(networkModule, mainActivityModule, repoListFragmentModule, repoDetailsModule))
        }
    }
}
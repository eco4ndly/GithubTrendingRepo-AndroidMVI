package com.eco4ndly.githubtrendingrepo.app

import android.app.Application
import com.eco4ndly.githubtrendingrepo.di.modules.networkModule
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
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GithubTrendingRepoApp)
            module { networkModule }
        }
    }
}
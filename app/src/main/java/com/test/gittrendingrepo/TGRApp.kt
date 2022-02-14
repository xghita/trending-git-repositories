package com.test.gittrendingrepo

import android.app.Application
import com.test.gittrendingrepo.di.networkModule
import com.test.gittrendingrepo.di.persistenceModule
import com.test.gittrendingrepo.di.providerModule
import com.test.gittrendingrepo.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TGRApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@TGRApp)
            modules(
                listOf(
                    viewModels,
                    networkModule,
                    persistenceModule,
                    providerModule
                )
            )
        }
    }
}
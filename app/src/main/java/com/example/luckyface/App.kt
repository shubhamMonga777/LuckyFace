package com.example.luckyface

import android.app.Application
import com.example.luckyface.data.db.AppDatabase
import com.example.luckyface.data.network.Myapi
import com.example.luckyface.data.network.NetworkConnectionInterceptor
import com.example.luckyface.data.repositories.AuthUserRepository
import com.example.luckyface.ui.auth.viewmodel.AuthViewModelFactory
import com.example.luckyface.ui.main.profile.viewmodel.ProfileViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { Myapi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { AuthUserRepository(instance(), instance()) }
        bind() from singleton { AuthViewModelFactory(instance()) }
        bind() from singleton { ProfileViewModelFactory(instance()) }
    }
}
package com.mashup.lemonsatang.di

import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.data.MonndayRepositoryImpl
import com.mashup.lemonsatang.data.prefs.SharedPreferenceStorage
import com.mashup.lemonsatang.data.remote.MonndayRemoteDataSource
import com.mashup.lemonsatang.data.remote.MonndayRemoteDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<MonndayRepository> { MonndayRepositoryImpl(get()) }
    single<MonndayRemoteDataSource> { MonndayRemoteDataSourceImpl(get()) }

    single { SharedPreferenceStorage(androidContext()) }
}
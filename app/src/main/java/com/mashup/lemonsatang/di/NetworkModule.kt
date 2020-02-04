package com.mashup.lemonsatang.di

import com.mashup.lemonsatang.network.MonndayApiService
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val MONNDAY_API_URL = "https://www.spacedeploy.pw/"

val networkModule = module {
    single { (get() as Retrofit).create(MonndayApiService::class.java) }

    single {
        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .baseUrl(MONNDAY_API_URL)
            .build()
    }

    single { GsonConverterFactory.create() as Converter.Factory }
    single { RxJava2CallAdapterFactory.create() as CallAdapter.Factory }
}
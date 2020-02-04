package com.mashup.lemonsatang.di

import com.mashup.lemonsatang.network.MonndayApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val MONNDAY_API_URL = "http://54.180.99.3:8080/"

private const val KEY_AUTORIZATION = "Authorization"
private const val KEY_PROVIDER = "Provider"
private const val KEY_TIMEZONE = "Timezone"
private const val VAL_AUTORIZATION = "spacedeploy123456"
private const val VAL_PROVIDER = "kakao"
private const val VAL_TIMEZONE = "Asia/Seoul"

val networkModule = module {
    single { (get() as Retrofit).create(MonndayApiService::class.java) }

    single {
        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
            .baseUrl(MONNDAY_API_URL)
            .build()
    }

    single { GsonConverterFactory.create() as Converter.Factory }
    single { RxJava2CallAdapterFactory.create() as CallAdapter.Factory }
    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor {chain ->
                val originRequest = chain.request()
                val newRequest = originRequest.newBuilder()
                    .header(KEY_AUTORIZATION, VAL_AUTORIZATION)
                    .header(KEY_PROVIDER, VAL_PROVIDER)
                    .header(KEY_TIMEZONE, VAL_TIMEZONE)
                    .method(originRequest.method(), originRequest.body())
                    .build()

                chain.proceed(newRequest)
            }
            .build() as OkHttpClient
    }
}
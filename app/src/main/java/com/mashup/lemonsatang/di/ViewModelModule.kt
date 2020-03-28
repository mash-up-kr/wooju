package com.mashup.lemonsatang.di

import com.mashup.lemonsatang.ui.main.MainViewModel
import com.mashup.lemonsatang.ui.remindlist.RemindListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { RemindListViewModel(get()) }
}
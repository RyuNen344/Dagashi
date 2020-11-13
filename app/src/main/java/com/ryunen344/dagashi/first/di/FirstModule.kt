package com.ryunen344.dagashi.first.di

import androidx.lifecycle.ViewModel
import com.ryunen344.dagashi.di.ViewModelKey
import com.ryunen344.dagashi.first.FirstViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FirstModule {

    @Binds
    @IntoMap
    @ViewModelKey(FirstViewModel::class)
    abstract fun bindAccountViewModel(viewModel: FirstViewModel): ViewModel
}

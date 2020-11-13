package com.ryunen344.dagashi.first.di

import com.ryunen344.dagashi.first.FirstFragment
import dagger.Subcomponent

@Subcomponent(modules = [FirstModule::class])
interface FirstComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FirstComponent
    }

    fun inject(fragment: FirstFragment)
}

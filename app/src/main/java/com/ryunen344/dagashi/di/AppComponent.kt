package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.DagashiApplication
import com.ryunen344.dagashi.first.di.FirstComponent
import com.ryunen344.dagashi.repository.FirstRepository
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppModuleBinds::class,
        ViewModelModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DagashiApplication): AppComponent
    }

    val firstRepository: FirstRepository

    fun firstComponent(): FirstComponent.Factory
}

@Module(
    subcomponents = [
        FirstComponent::class
    ]
)
object SubcomponentsModule

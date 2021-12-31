package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.BuildConfig
import com.ryunen344.dagashi.data.api.DagashiApi
import com.ryunen344.dagashi.data.api.impl.DagashiApiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @ApiEndpoint
    @Singleton
    fun provideApiEndpoint(): String = BuildConfig.API_ENDPOINT

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient {
        return HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
            engine {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideDagashiApiImpl(httpClient: HttpClient, @ApiEndpoint apiEndpoint: String): DagashiApiImpl {
        return DagashiApiImpl(httpClient, apiEndpoint)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ApiModuleBinds {
        @Binds
        abstract fun bindDagashiApi(impl: DagashiApiImpl): DagashiApi
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiEndpoint

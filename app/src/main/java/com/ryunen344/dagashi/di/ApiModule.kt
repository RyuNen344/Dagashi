package com.ryunen344.dagashi.di

import com.ryunen344.dagashi.BuildConfig
import com.ryunen344.dagashi.data.api.DagashiApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    @ApiEndpoint
    @Singleton
    fun provideApiEndpoint(): String = BuildConfig.API_ENDPOINT

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
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
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideDagashiApi(httpClient: HttpClient, @ApiEndpoint apiEndpoint: String): DagashiApi {
        return DagashiApi(httpClient, apiEndpoint)
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiEndpoint

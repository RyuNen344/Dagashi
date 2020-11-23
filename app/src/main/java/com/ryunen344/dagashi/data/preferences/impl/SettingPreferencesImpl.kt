package com.ryunen344.dagashi.data.preferences.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.ryunen344.dagashi.data.preferences.SettingPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class SettingPreferencesImpl @Inject constructor(context: Context) : SettingPreferences {

    private val dataStore: DataStore<Preferences> by lazy { context.createDataStore(name = DATA_STORE_NAME) }

    override suspend fun updateIsOpenInWebView(isOpen: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.IS_OPEN_IN_WEB_VIEW.key] = isOpen
        }
    }

    override val isOpenInWebView: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading preferences.")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.IS_OPEN_IN_WEB_VIEW.key] ?: PreferencesKeys.IS_OPEN_IN_WEB_VIEW.defaultValue
        }

    private object PreferencesKeys {
        val IS_OPEN_IN_WEB_VIEW = PreferencesKeySet(preferencesKey("isOpenInWebView"), true)
    }

    private class PreferencesKeySet<T>(
        val key: Preferences.Key<T>,
        val defaultValue: T
    )

    companion object {
        private const val DATA_STORE_NAME = "setting"
    }
}



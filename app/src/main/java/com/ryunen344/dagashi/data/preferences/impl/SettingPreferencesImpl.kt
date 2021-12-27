package com.ryunen344.dagashi.data.preferences.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ryunen344.dagashi.data.preferences.SettingPreferences
import com.ryunen344.dagashi.util.ext.format
import com.ryunen344.dagashi.util.ext.parse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import java.time.OffsetDateTime
import javax.inject.Inject

class SettingPreferencesImpl @Inject constructor(private val context: Context) : SettingPreferences {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    override val mileStoneLastUpdateAt: Flow<OffsetDateTime?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading preferences.")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.MILE_STONE_LAST_UPDATE_AT.key]?.parse()
        }

    override val isOpenInWebView: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading preferences.")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.IS_OPEN_IN_WEB_VIEW.key] ?: checkNotNull(PreferencesKeys.IS_OPEN_IN_WEB_VIEW.defaultValue)
        }

    override suspend fun updateMileStoneLastUpdateAt(time: OffsetDateTime) {
        context.dataStore.edit {
            it[PreferencesKeys.MILE_STONE_LAST_UPDATE_AT.key] = time.format()
        }
    }

    override suspend fun updateIsOpenInWebView(isOpen: Boolean) {
        context.dataStore.edit {
            it[PreferencesKeys.IS_OPEN_IN_WEB_VIEW.key] = isOpen
        }
    }

    private object PreferencesKeys {
        val MILE_STONE_LAST_UPDATE_AT = PreferencesKeySet<String, OffsetDateTime?>(stringPreferencesKey("mile_stone_last_update_at"), null)
        val IS_OPEN_IN_WEB_VIEW = PreferencesKeySet(booleanPreferencesKey("is_open_in_web_view"), true)
    }

    private class PreferencesKeySet<T, R>(
        val key: Preferences.Key<T>,
        val defaultValue: R?
    )

    companion object {
        private const val DATA_STORE_NAME = "setting"
    }
}

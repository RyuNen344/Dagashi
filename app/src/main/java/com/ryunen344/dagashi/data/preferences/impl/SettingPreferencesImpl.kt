package com.ryunen344.dagashi.data.preferences.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.ryunen344.dagashi.data.preferences.SettingPreferences
import com.ryunen344.dagashi.util.ext.format
import com.ryunen344.dagashi.util.ext.parse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.threeten.bp.OffsetDateTime
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class SettingPreferencesImpl @Inject constructor(context: Context) : SettingPreferences {

    private val dataStore: DataStore<Preferences> by lazy { context.createDataStore(name = DATA_STORE_NAME) }

    override val mileStoneLastUpdateAt: Flow<OffsetDateTime?> = dataStore.data
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

    override val isOpenInWebView: Flow<Boolean> = dataStore.data
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
        dataStore.edit {
            it[PreferencesKeys.MILE_STONE_LAST_UPDATE_AT.key] = time.format()
        }
    }

    override suspend fun updateIsOpenInWebView(isOpen: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.IS_OPEN_IN_WEB_VIEW.key] = isOpen
        }
    }

    private object PreferencesKeys {
        val MILE_STONE_LAST_UPDATE_AT = PreferencesKeySet<String, OffsetDateTime?>(preferencesKey("mile_stone_last_update_at"), null)
        val IS_OPEN_IN_WEB_VIEW = PreferencesKeySet<Boolean, Boolean>(preferencesKey("is_open_in_web_view"), true)
    }

    private class PreferencesKeySet<T, R>(
        val key: Preferences.Key<T>,
        val defaultValue: R?
    )

    companion object {
        private const val DATA_STORE_NAME = "setting"
    }
}



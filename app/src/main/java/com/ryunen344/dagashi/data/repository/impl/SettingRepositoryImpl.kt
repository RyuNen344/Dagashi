package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.preferences.SettingPreferences
import com.ryunen344.dagashi.data.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val settingPreferences: SettingPreferences) : SettingRepository {
    override suspend fun updateIsOpenInWebView(isOpen: Boolean) {
        settingPreferences.updateIsOpenInWebView(isOpen)
    }

    override fun isOpenInWebView(): Flow<Boolean> {
        return settingPreferences.isOpenInWebView
    }
}

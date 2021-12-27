package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.preferences.SettingPreferences
import com.ryunen344.dagashi.data.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val settingPreferences: SettingPreferences) : SettingRepository {
    override fun mileStoneLastUpdateAt(): Flow<OffsetDateTime?> {
        return settingPreferences.mileStoneLastUpdateAt
    }

    override suspend fun updateMileStoneLastUpdateAt(time: OffsetDateTime) {
        settingPreferences.updateMileStoneLastUpdateAt(time)
    }

    override fun isOpenInWebView(): Flow<Boolean> {
        return settingPreferences.isOpenInWebView
    }

    override suspend fun updateIsOpenInWebView(isOpen: Boolean) {
        settingPreferences.updateIsOpenInWebView(isOpen)
    }
}

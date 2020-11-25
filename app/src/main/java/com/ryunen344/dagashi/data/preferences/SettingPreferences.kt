package com.ryunen344.dagashi.data.preferences

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.OffsetDateTime

interface SettingPreferences {
    val mileStoneLastUpdateAt: Flow<OffsetDateTime?>
    val isOpenInWebView: Flow<Boolean>
    suspend fun updateMileStoneLastUpdateAt(time: OffsetDateTime)
    suspend fun updateIsOpenInWebView(isOpen: Boolean)
}

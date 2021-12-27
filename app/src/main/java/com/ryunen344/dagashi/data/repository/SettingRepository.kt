package com.ryunen344.dagashi.data.repository

import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

interface SettingRepository {
    fun mileStoneLastUpdateAt(): Flow<OffsetDateTime?>
    suspend fun updateMileStoneLastUpdateAt(time: OffsetDateTime)
    fun isOpenInWebView(): Flow<Boolean>
    suspend fun updateIsOpenInWebView(isOpen: Boolean)
}

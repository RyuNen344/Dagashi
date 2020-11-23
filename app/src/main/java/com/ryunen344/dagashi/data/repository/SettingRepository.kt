package com.ryunen344.dagashi.data.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun updateIsOpenInWebView(isOpen: Boolean)
    fun isOpenInWebView(): Flow<Boolean>
}

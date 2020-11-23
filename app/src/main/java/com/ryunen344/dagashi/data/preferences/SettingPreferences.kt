package com.ryunen344.dagashi.data.preferences

import kotlinx.coroutines.flow.Flow

interface SettingPreferences {
    suspend fun updateIsOpenInWebView(isOpen: Boolean)
    val isOpenInWebView: Flow<Boolean>
}

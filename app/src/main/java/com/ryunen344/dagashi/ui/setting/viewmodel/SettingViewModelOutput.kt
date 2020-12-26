package com.ryunen344.dagashi.ui.setting.viewmodel

import kotlinx.coroutines.flow.Flow

interface SettingViewModelOutput {
    val isOpenInWebView: Flow<Boolean>
}

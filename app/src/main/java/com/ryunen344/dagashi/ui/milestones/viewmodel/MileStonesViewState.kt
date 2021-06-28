package com.ryunen344.dagashi.ui.milestones.viewmodel

import com.ryunen344.dagashi.model.MileStone
import com.ryunen344.dagashi.util.ViewState
import kotlinx.coroutines.flow.StateFlow

interface MileStonesViewState : ViewState {
    val uiModel: StateFlow<UiModel>

    data class UiModel(val isRefreshing: Boolean, val mileStones: List<MileStone>?, val error: Throwable?) {
        val isLoading: Boolean
            get() = isRefreshing && error == null

        val isEmpty: Boolean
            get() = !isRefreshing && mileStones == null

        val hasError: Boolean
            get() = !isRefreshing && error != null

        companion object {
            val NonInitialized = UiModel(isRefreshing = false, mileStones = null, error = null)
        }
    }
}

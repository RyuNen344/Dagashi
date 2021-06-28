package com.ryunen344.dagashi.ui.milestones.viewmodel

import com.ryunen344.dagashi.util.ViewEvent
import kotlinx.coroutines.flow.SharedFlow

interface MileStonesViewEvent : ViewEvent {
    val isUpdated: SharedFlow<Unit>
}

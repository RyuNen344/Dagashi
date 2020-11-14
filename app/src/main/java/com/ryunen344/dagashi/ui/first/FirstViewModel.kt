package com.ryunen344.dagashi.ui.first

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryunen344.dagashi.data.repository.FirstRepository
import com.ryunen344.dagashi.data.repository.IssueRepository
import com.ryunen344.dagashi.data.repository.MileStoneRepository
import kotlinx.coroutines.launch

class FirstViewModel @ViewModelInject constructor(
    private val firstRepository: FirstRepository,
    private val mileStoneRepository: MileStoneRepository,
    private val issueRepository: IssueRepository
) : ViewModel() {
    fun getStringFromRepository(): String {
        return firstRepository.getString()
    }

    fun getMileStones() {
        viewModelScope.launch {
            mileStoneRepository.mileStones()
        }
    }
}

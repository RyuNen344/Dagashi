package com.ryunen344.dagashi.first

import androidx.lifecycle.ViewModel
import com.ryunen344.dagashi.repository.FirstRepository
import javax.inject.Inject

class FirstViewModel @Inject constructor(private val firstRepository: FirstRepository) : ViewModel() {
    fun getStringFromRepository(): String {
        return firstRepository.getString()
    }
}

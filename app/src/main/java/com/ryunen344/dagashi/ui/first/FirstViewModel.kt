package com.ryunen344.dagashi.ui.first

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ryunen344.dagashi.data.repository.FirstRepository

class FirstViewModel @ViewModelInject constructor(private val firstRepository: FirstRepository) : ViewModel() {
    fun getStringFromRepository(): String {
        return firstRepository.getString()
    }
}

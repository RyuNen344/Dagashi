package com.ryunen344.dagashi.first

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ryunen344.dagashi.repository.FirstRepository

class FirstViewModel @ViewModelInject constructor(private val firstRepository: FirstRepository) : ViewModel() {
    fun getStringFromRepository(): String {
        return firstRepository.getString()
    }
}

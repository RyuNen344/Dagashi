package com.ryunen344.dagashi.repository.impl

import com.ryunen344.dagashi.repository.FirstRepository
import javax.inject.Inject

class FirstRepositoryImpl @Inject constructor() : FirstRepository {
    override fun getString(): String {
        return "FirstRepositoryImpl $this"
    }
}

package com.ryunen344.dagashi.repository.impl

import com.ryunen344.dagashi.repository.FirstRepository

class FirstRepositoryImpl : FirstRepository {
    override fun getString(): String {
        return "FirstRepositoryImpl $this"
    }
}

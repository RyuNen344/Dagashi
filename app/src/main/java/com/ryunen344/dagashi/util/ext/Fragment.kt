package com.ryunen344.dagashi.util.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ryunen344.dagashi.util.observe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <reified T> Fragment.bind(
    source: Flow<T>,
    crossinline action: (T) -> Unit
) {
    source.observe(viewLifecycleOwner) {
        action(it)
    }
}

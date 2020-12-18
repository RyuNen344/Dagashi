package com.ryunen344.dagashi.util.ext

import androidx.fragment.app.Fragment
import com.ryunen344.dagashi.util.observe
import kotlinx.coroutines.flow.Flow

inline fun <reified T> Fragment.bind(
    source: Flow<T>,
    crossinline action: (T) -> Unit
) {
    source.observe(viewLifecycleOwner) {
        action(it)
    }
}

package com.ryunen344.dagashi.util.ext

import android.view.View
import com.ryunen344.dagashi.util.DebouncingOnClickListener

fun View.setDebouncingOnClickListener(onClick: ((View) -> Unit)?) {
    onClick?.let { actualOnClick ->
        setOnClickListener(object : DebouncingOnClickListener {
            override fun doClick(view: View) = actualOnClick(view)
        })
    } ?: setOnClickListener(null)
}

package com.ryunen344.dagashi.util

import android.view.View

interface DebouncingOnClickListener : View.OnClickListener {

    override fun onClick(view: View) {
        if (enabled) {
            enabled = false
            view.postDelayed(ENABLE_AGAIN, DURATION)
            doClick(view)
        }
    }

    fun doClick(view: View)

    companion object {
        private var enabled: Boolean = true
        private val ENABLE_AGAIN = { enabled = true }
        private const val DURATION = 500L
    }
}

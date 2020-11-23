package com.ryunen344.dagashi.model

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.graphics.ColorUtils
import com.ryunen344.dagashi.R

data class Label(
    val name: String,
    val description: String,
    val color: String
) {
    val labelIssueUrl: String
        get() = "https://github.com/AndroidDagashi/AndroidDagashi/issues?q=label%3A%22$name%22"

    val tipColorInt: Int
        @ColorInt
        get() = Color.parseColor("#$color")

    val tipTextColorResId: Int
        @ColorRes
        get() {
            return if (isColorDark(tipColorInt)) R.color.black else R.color.white
        }

    private fun isColorDark(@ColorInt color: Int): Boolean {
        val darkRatio = ColorUtils.calculateContrast(color, Color.BLACK)
        val lightRatio = ColorUtils.calculateContrast(color, Color.WHITE)
        return darkRatio > lightRatio + 0.5
    }
}

package com.ryunen344.dagashi.util.ext

import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.util.DisplayMetrics
import androidx.browser.customtabs.CustomTabsIntent
import timber.log.Timber

fun Context.startChromeTabs(url: String) {
    try {
        val tabsIntent: CustomTabsIntent = CustomTabsIntent.Builder().build()
        tabsIntent.launchUrl(this, Uri.parse(url))
    } catch (e: ActivityNotFoundException) {
        Timber.e(e)
    }
}

inline val Context.displayMetrics: DisplayMetrics
    get() = resources.displayMetrics

fun Context.dp2px(dp: Number) = (dp.toFloat() * displayMetrics.density + 0.5f).toInt()

fun Context.sp2px(sp: Number) = (sp.toFloat() * displayMetrics.scaledDensity + 0.5f).toInt()

fun Context.px2dp(px: Number) = (px.toFloat() / displayMetrics.density).toInt()

fun Context.px2sp(px: Number) = (px.toFloat() / displayMetrics.scaledDensity).toInt()

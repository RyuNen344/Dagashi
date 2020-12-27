package com.ryunen344.dagashi.initializer

import android.content.Context
import android.graphics.Color
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.ryunen344.dagashi.BuildConfig
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.initializer.core.AppInitializer
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class EmojiInitializer @Inject constructor(@ApplicationContext private val applicationContext: Context) : AppInitializer {
    override fun initialize() {
        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs
        )
        val config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
            // すべての絵文字を最新の絵文字に切り替えるか
            .setReplaceAll(true)
            // EmojiCompat よって、切り替わった絵文字に色をつける（デバック目的）
            .setEmojiSpanIndicatorEnabled(BuildConfig.DEBUG)
            .setEmojiSpanIndicatorColor(Color.GREEN)
            .registerInitCallback(object : EmojiCompat.InitCallback() {
                override fun onInitialized() {
                    Timber.d("EmojiCompat initialized")
                }

                override fun onFailed(throwable: Throwable?) {
                    Timber.e(throwable, "EmojiCompat initialization failed")
                }
            })
        EmojiCompat.init(config)
    }
}

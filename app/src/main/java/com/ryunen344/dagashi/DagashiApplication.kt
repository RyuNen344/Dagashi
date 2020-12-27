package com.ryunen344.dagashi

import android.app.Application
import android.graphics.Color
import android.os.StrictMode
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DagashiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()
                    .detectLeakedClosableObjects()
                    .detectLeakedRegistrationObjects()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .build()
            )
        }

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

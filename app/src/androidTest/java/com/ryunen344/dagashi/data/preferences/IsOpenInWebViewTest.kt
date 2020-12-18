package com.ryunen344.dagashi.data.preferences

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ryunen344.dagashi.data.preferences.impl.SettingPreferencesImpl
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import com.ryunen344.dagashi.test.runBlockingTest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * テスト作り直し
 */
@RunWith(AndroidJUnit4::class)
class IsOpenInWebViewTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val settingPreferencesImpl: SettingPreferencesImpl =
        SettingPreferencesImpl(ApplicationProvider.getApplicationContext())

    @Test
    fun testImpl() {
        runBlocking {
            settingPreferencesImpl.isOpenInWebView.onEach {
                println("isOpen $it")
            }.launchIn(mainCoroutineTestRule)

            mainCoroutineTestRule.runBlockingTest {
                settingPreferencesImpl.updateIsOpenInWebView(true)
            }
        }
    }
}

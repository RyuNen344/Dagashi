package com.ryunen344.dagashi.data.preferences

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ryunen344.dagashi.data.preferences.impl.SettingPreferencesImpl
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * テスト作り直し
 */
@RunWith(AndroidJUnit4::class)
class MileStoneLastUpdateAtTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val settingPreferencesImpl: SettingPreferencesImpl =
        SettingPreferencesImpl(ApplicationProvider.getApplicationContext<Context>())

    @Before
    fun setup() {
        settingPreferencesImpl
    }

    @Test
    fun testImpl() {
        mainCoroutineTestRule.runBlockingTest {
        }
    }
}

package com.ryunen344.dagashi.data.preferences

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ryunen344.dagashi.data.preferences.impl.SettingPreferencesImpl
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import com.ryunen344.dagashi.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * DataStore can't run with Robolectric
 */
@RunWith(AndroidJUnit4::class)
class IsOpenInWebViewTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val settingPreferencesImpl: SettingPreferencesImpl =
        SettingPreferencesImpl(ApplicationProvider.getApplicationContext())

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

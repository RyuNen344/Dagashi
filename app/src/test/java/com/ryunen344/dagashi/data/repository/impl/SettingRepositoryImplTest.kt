package com.ryunen344.dagashi.data.repository.impl

import com.ryunen344.dagashi.data.preferences.SettingPreferences
import com.ryunen344.dagashi.test.MainCoroutineTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import java.time.OffsetDateTime

class SettingRepositoryImplTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val mockSettingPreferences: SettingPreferences = mockk()

    private val settingRepositoryImpl = SettingRepositoryImpl(mockSettingPreferences)

    @Test
    fun mileStoneLastUpdateAt() {
        coEvery { mockSettingPreferences.mileStoneLastUpdateAt } coAnswers { flowOf(null) }

        settingRepositoryImpl.mileStoneLastUpdateAt()

        coVerify { mockSettingPreferences.mileStoneLastUpdateAt }
        confirmVerified(mockSettingPreferences)
    }

    @Test
    fun updateMileStoneLastUpdateAt() {
        mainCoroutineTestRule.runBlockingTest {
            val time = OffsetDateTime.now()

            coEvery { mockSettingPreferences.updateMileStoneLastUpdateAt(time) } coAnswers {}

            settingRepositoryImpl.updateMileStoneLastUpdateAt(time)

            coVerify { mockSettingPreferences.updateMileStoneLastUpdateAt(time) }
            confirmVerified(mockSettingPreferences)
        }
    }

    @Test
    fun isOpenInWebView() {
        coEvery { mockSettingPreferences.isOpenInWebView } coAnswers { flowOf(true) }

        settingRepositoryImpl.isOpenInWebView()

        coVerify { mockSettingPreferences.isOpenInWebView }
        confirmVerified(mockSettingPreferences)
    }

    @Test
    fun updateIsOpenInWebView() {
        mainCoroutineTestRule.runBlockingTest {
            coEvery { mockSettingPreferences.updateIsOpenInWebView(true) } coAnswers {}

            settingRepositoryImpl.updateIsOpenInWebView(true)

            coVerify { mockSettingPreferences.updateIsOpenInWebView(true) }
        }
        confirmVerified(mockSettingPreferences)
    }
}

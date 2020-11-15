package com.ryunen344.dagashi.ui.setting

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.ryunen344.dagashi.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

        when (rootKey) {
            "categoryTheme" -> {
                Timber.wtf("categoryTheme")
            }
            else            -> {
                Timber.wtf("else")
            }
        }
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        val notificationPreference = SwitchPreferenceCompat(context)
        notificationPreference.key = "notifications"
        notificationPreference.title = "Enable message notifications"

        val notificationCategory = PreferenceCategory(context)
        notificationCategory.key = "notifications_category"
        notificationCategory.title = "Notifications"
        screen.addPreference(notificationCategory)
        notificationCategory.addPreference(notificationPreference)

        val feedbackPreference = Preference(context)
        feedbackPreference.key = "feedback"
        feedbackPreference.title = "Send feedback"
        feedbackPreference.summary = "Report technical issues or suggest new features"

        val helpCategory = PreferenceCategory(context)
        helpCategory.key = "help"
        helpCategory.title = "Help"
        screen.addPreference(helpCategory)
        helpCategory.addPreference(feedbackPreference)

        preferenceScreen = screen
    }
}

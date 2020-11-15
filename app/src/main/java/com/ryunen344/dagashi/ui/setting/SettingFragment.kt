package com.ryunen344.dagashi.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.ryunen344.dagashi.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
    }
}

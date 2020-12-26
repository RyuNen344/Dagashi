package com.ryunen344.dagashi.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentSettingBinding
import com.ryunen344.dagashi.ui.setting.viewmodel.SettingViewModel
import com.ryunen344.dagashi.util.ext.bind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks

@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {

    private val viewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSettingBinding.bind(view)
        binding.apply {
            toolbar.setupWithNavController(findNavController())

            itemSettingBrowser.clicks()
                .onEach {
                    viewModel.toggleSettingBrowser(itemSettingBrowser.isChecked)
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            itemSettingForceRefresh.clicks()
                .debounce(500)
                .onEach {
                    viewModel.refreshMileStones()
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        viewModel.apply {
            bind(isOpenInWebView) {
                binding.itemSettingBrowser.isChecked = it
            }
        }
    }
}

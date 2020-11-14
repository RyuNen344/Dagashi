package com.ryunen344.dagashi.ui.first

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FirstFragment : Fragment(R.layout.fragment_first) {

    private val viewModel: FirstViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentFirstBinding.bind(view).apply {
            Timber.wtf(viewModel.getStringFromRepository())
        }
    }
}

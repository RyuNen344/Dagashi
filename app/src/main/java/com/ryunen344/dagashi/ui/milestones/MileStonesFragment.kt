package com.ryunen344.dagashi.ui.milestones

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentMileStonesBinding
import com.ryunen344.dagashi.util.ext.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MileStonesFragment : Fragment(R.layout.fragment_mile_stones) {

    private val viewModel: MileStonesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMileStonesBinding.bind(view)
        val adapter = MileStonesAdapter {
            // TODO: 2020/11/14
        }

        binding.viewRecycler.adapter = adapter

        bind(viewModel.mileStones) {
            adapter.setData(it)
        }
    }
}

package com.ryunen344.dagashi.ui.milestones

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
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
            findNavController().navigate(MileStonesFragmentDirections.actionMileStonesToFirst(it.path))
        }

        binding.viewRecycler.adapter = adapter
        binding.toolbar.setupWithNavController(findNavController())

        bind(viewModel.mileStones) {
            adapter.setData(it)
        }

        bind(viewModel.isUpdated) {
            Toast.makeText(requireContext(), "更新されたで", Toast.LENGTH_SHORT).show()
        }
    }
}

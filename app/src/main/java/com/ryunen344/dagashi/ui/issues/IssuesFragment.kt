package com.ryunen344.dagashi.ui.issues

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentIssuesBinding
import com.ryunen344.dagashi.util.ext.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssuesFragment : Fragment(R.layout.fragment_issues) {

    private val args: IssuesFragmentArgs by navArgs()

    private val viewModel: IssuesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentIssuesBinding.bind(view)
        val adapter = IssuesAdapter()

        binding.viewRecycler.adapter = adapter

        bind(viewModel.issues) {
            adapter.setData(it)
        }

        bind(viewModel.isUpdated) {
            Toast.makeText(requireContext(), "更新されたで", Toast.LENGTH_SHORT).show()
        }

        viewModel.refresh(args.path)
    }
}

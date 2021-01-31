package com.ryunen344.dagashi.ui.issues.stash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentStashBinding
import com.ryunen344.dagashi.ui.issues.IssuesAdapter
import com.ryunen344.dagashi.ui.issues.path.PathIssuesFragmentDirections
import com.ryunen344.dagashi.ui.issues.stash.viewmodel.StashIssuesViewModel
import com.ryunen344.dagashi.ui.issues.stash.viewmodel.StashIssuesViewModelOutput
import com.ryunen344.dagashi.util.TextViewClickMovement
import com.ryunen344.dagashi.util.ext.bind
import com.ryunen344.dagashi.util.ext.startChromeTabs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StashIssuesFragment : Fragment(R.layout.fragment_stash) {

    private val viewModel: StashIssuesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentStashBinding.bind(view)
        val adapter = IssuesAdapter(
            onLabelClickListener = { label ->
                viewModel.inputUrl(label.labelIssueUrl)
            },
            onIssueClickListener = { url ->
                viewModel.inputUrl(url)
            },
            onStashClickListener = { issue ->
                viewModel.toggleStash(issue)
            },
            object : TextViewClickMovement.OnTextViewClickMovementListener {
                override fun onLinkClicked(linkText: String, linkType: TextViewClickMovement.LinkType) {
                    if (linkType == TextViewClickMovement.LinkType.WEB_URL) {
                        viewModel.inputUrl(linkText)
                    }
                }
            }
        )

        binding.apply {
            viewRecycler.adapter = adapter
            toolbar.setupWithNavController(findNavController())
        }

        viewModel.apply {
            bind(issues) {
                adapter.setData(it)
            }

            bind(openUrlModel) {
                when (it) {
                    is StashIssuesViewModelOutput.OpenUrlModel.WebView -> {
                        findNavController().navigate(PathIssuesFragmentDirections.actionIssuesToWeb(it.url))
                    }
                    is StashIssuesViewModelOutput.OpenUrlModel.ChromeTabs -> {
                        requireContext().startChromeTabs(it.url)
                    }
                }
            }
        }
    }
}

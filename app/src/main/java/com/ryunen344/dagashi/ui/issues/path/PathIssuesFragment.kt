package com.ryunen344.dagashi.ui.issues.path

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentIssuesBinding
import com.ryunen344.dagashi.ui.issues.IssuesAdapter
import com.ryunen344.dagashi.ui.issues.path.viewmodel.PathIssuesViewModel
import com.ryunen344.dagashi.ui.issues.path.viewmodel.PathIssuesViewModelOutput
import com.ryunen344.dagashi.util.TextViewClickMovement
import com.ryunen344.dagashi.util.ext.assistedViewModels
import com.ryunen344.dagashi.util.ext.bind
import com.ryunen344.dagashi.util.ext.startChromeTabs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PathIssuesFragment : Fragment(R.layout.fragment_issues) {

    private val args: PathIssuesFragmentArgs by navArgs()

    @Inject
    lateinit var issuesViewModelAssistedFactory: PathIssuesViewModel.AssistedFactory

    private val viewModel: PathIssuesViewModel by assistedViewModels {
        issuesViewModelAssistedFactory.create(
            args.number,
            args.path
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentIssuesBinding.bind(view)
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

            bind(isUpdated) {
                Toast.makeText(requireContext(), R.string.snack_bar_update, Toast.LENGTH_SHORT).show()
            }

            bind(openUrlModel) {
                when (it) {
                    is PathIssuesViewModelOutput.OpenUrlModel.WebView -> {
                        findNavController().navigate(PathIssuesFragmentDirections.actionIssuesToWeb(it.url))
                    }
                    is PathIssuesViewModelOutput.OpenUrlModel.ChromeTabs -> {
                        requireContext().startChromeTabs(it.url)
                    }
                }
            }
        }
    }
}

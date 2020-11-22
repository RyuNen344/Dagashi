package com.ryunen344.dagashi.ui.issues

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentIssuesBinding
import com.ryunen344.dagashi.util.TextViewClickMovement
import com.ryunen344.dagashi.util.ext.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssuesFragment : Fragment(R.layout.fragment_issues) {

    private val args: IssuesFragmentArgs by navArgs()

    private val viewModel: IssuesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refresh(args.number, args.path)
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
                Toast.makeText(requireContext(), "更新されたで", Toast.LENGTH_SHORT).show()
            }

            bind(openUrlModel) {
                when (it) {
                    is IssuesViewModel.OpenUrlModel.WebView -> {
                        findNavController().navigate(IssuesFragmentDirections.actionIssuesToWeb(it.url))
                    }
                    is IssuesViewModel.OpenUrlModel.ActionView -> {

                    }
                }
            }
        }
    }
}

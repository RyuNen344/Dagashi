package com.ryunen344.dagashi.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentSearchBinding
import com.ryunen344.dagashi.ui.issues.IssuesAdapter
import com.ryunen344.dagashi.ui.search.viewmodel.SearchViewModel
import com.ryunen344.dagashi.ui.search.viewmodel.SearchViewModelOutput
import com.ryunen344.dagashi.util.TextViewClickMovement
import com.ryunen344.dagashi.util.ext.bind
import com.ryunen344.dagashi.util.ext.hideKeyboard
import com.ryunen344.dagashi.util.ext.showKeyboard
import com.ryunen344.dagashi.util.ext.startChromeTabs
import com.ryunen344.dagashi.util.queryTextFocusChangeEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.appcompat.queryTextChangeEvents

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSearchBinding.bind(view)
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
            viewSearch.apply {
                isIconified = false
                setOnCloseListener {
                    findNavController().navigateUp()
                    true
                }
                queryTextFocusChangeEvents()
                    .drop(1)
                    .onEach {
                        if (it.hasFocus) {
                            showKeyboard(it.view.findFocus())
                        } else {
                            hideKeyboard()
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                queryTextChangeEvents()
                    .drop(1)
                    .onEach {
                        viewModel.searchIssue(it.queryText.toString())
                        if (it.isSubmitted) {
                            it.view.clearFocus()
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        viewModel.apply {
            bind(issues) {
                adapter.setData(it)
            }

            bind(openUrlModel) {
                when (it) {
                    is SearchViewModelOutput.OpenUrlModel.WebView -> {
                        findNavController().navigate(SearchFragmentDirections.actionIssuesToWeb(it.url))
                    }
                    is SearchViewModelOutput.OpenUrlModel.ChromeTabs -> {
                        requireContext().startChromeTabs(it.url)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }
}

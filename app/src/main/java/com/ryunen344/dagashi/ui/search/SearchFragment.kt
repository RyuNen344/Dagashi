package com.ryunen344.dagashi.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentSearchBinding
import com.ryunen344.dagashi.ui.search.viewmodel.SearchViewModel
import com.ryunen344.dagashi.util.ext.hideKeyboard
import com.ryunen344.dagashi.util.ext.showKeyboard
import com.ryunen344.dagashi.util.queryTextFocusChangeEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.appcompat.queryTextChangeEvents
import timber.log.Timber


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSearchBinding.bind(view)
        binding.apply {
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
                        Timber.wtf("query ${it.queryText}")
                        if (it.isSubmitted) {
                            it.view.clearFocus()
                        }
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

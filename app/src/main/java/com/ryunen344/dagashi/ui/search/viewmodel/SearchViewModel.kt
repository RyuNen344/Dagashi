package com.ryunen344.dagashi.ui.search.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ryunen344.dagashi.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher

class SearchViewModel @ViewModelInject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel(), SearchViewModelInput, SearchViewModelOutput {
}

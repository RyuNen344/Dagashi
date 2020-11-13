package com.ryunen344.dagashi.first

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ryunen344.dagashi.DagashiApplication
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentFirstBinding
import timber.log.Timber
import javax.inject.Inject

class FirstFragment : Fragment(R.layout.fragment_first) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FirstViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as DagashiApplication).appComponent.firstComponent().create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentFirstBinding.bind(view).apply {
            Timber.wtf(viewModel.getStringFromRepository())
        }
    }
}

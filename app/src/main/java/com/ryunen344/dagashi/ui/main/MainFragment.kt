package com.ryunen344.dagashi.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ryunen344.dagashi.R
import com.ryunen344.dagashi.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentMainBinding.bind(view).apply {
            buttonFirst.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainToFirst())
            }

            buttonSecond.setOnClickListener {
            }

            buttonThird.setOnClickListener {
            }
        }
    }
}

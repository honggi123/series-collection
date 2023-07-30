package com.example.series_collector.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.series_collector.databinding.FragmentHomeSeriesBinding
import com.example.series_collector.ui.adapters.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment() : Fragment() {

    lateinit private var binding: FragmentHomeSeriesBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeSeriesBinding.inflate(inflater, container, false)
        binding.apply {
            val adapter = CategoryAdapter()
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
            rvCategorys.adapter = adapter

            layoutRefresh.setOnRefreshListener(){
                homeViewModel.updateSeries()
                layoutRefresh.isRefreshing = false
            }

            subscribeUi(adapter)
        }

        return binding.root
    }

    private fun subscribeUi(adapter: CategoryAdapter) {
        homeViewModel.categoryContents.observe(viewLifecycleOwner) { contents ->
            adapter.submitList(contents)
        }
    }

}

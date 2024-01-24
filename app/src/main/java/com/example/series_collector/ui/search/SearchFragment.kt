package com.example.series_collector.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.series_collector.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    lateinit private var binding: FragmentSearchBinding
    private val adapter = SearchSeriesAdapter()
    private val searchViewModel: SearchViewModel by viewModels(
        ownerProducer = { this.parentFragment as Fragment }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            rvSearchSeries.adapter = adapter
        }

        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        parentFragment?.let {
            searchViewModel.filteredSeries.observe(
                it.viewLifecycleOwner, { contents ->
                    adapter.submitList(contents)
                })
        }
    }
}


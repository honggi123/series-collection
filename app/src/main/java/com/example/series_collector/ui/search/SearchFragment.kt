package com.example.series_collector.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.series_collector.data.model.Series
import com.example.series_collector.databinding.FragmentSearchBinding
import com.example.series_collector.ui.adapters.SearchSeriesAdapter
import kotlinx.coroutines.launch

private const val SEARCH_PAGE_KEY = "searchKey"

class SearchFragment : Fragment() {

    lateinit private var binding: FragmentSearchBinding
    private val adapter = SearchSeriesAdapter()
    private val filterTypes = SearchFilterType.values().toList()
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
        val pageIdx = arguments?.getInt(SEARCH_PAGE_KEY) ?: 0

        lifecycleScope.launch {
            parentFragment?.let {
                searchViewModel.searchedResult.observe(
                    it.viewLifecycleOwner, { contents ->
                        val newContents = filterSeries(contents, filterTypes.get(pageIdx))
                        adapter.submitList(newContents)
                    })
            }
        }
    }

    private fun filterSeries(
        seriesList: List<Series>,
        filteringType: SearchFilterType
    ): List<Series> {
        val seriesToShow = ArrayList<Series>()
        for (series in seriesList) {
            when (filteringType) {
                SearchFilterType.ALL_FILTER_PAGE -> seriesToShow.add(series)
                SearchFilterType.FICTION_FILTER_PAGE -> if (series.genre == 1) {
                    seriesToShow.add(series)
                }
                SearchFilterType.TRAVEL_FILTER_PAGE -> if (series.genre == 2) {
                    seriesToShow.add(series)
                }
            }
        }
        return seriesToShow
    }

    companion object {
        fun newInstance(position: Int) = SearchFragment().apply {
            arguments = Bundle().apply {
                putInt(SEARCH_PAGE_KEY, position)
            }
        }
    }

}


package com.example.series_collector.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.series_collector.data.model.Series
import com.example.series_collector.databinding.FragmentSearchBinding
import com.example.series_collector.ui.adapters.SearchSeriesAdapter

private const val PAGE_POSITION_BUNDLE_KEY = "searchKey"
private const val NO_PAGE_POSITION_INDEX = 0

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
        val pageIdx = arguments?.getInt(PAGE_POSITION_BUNDLE_KEY) ?: NO_PAGE_POSITION_INDEX
            parentFragment?.let {
                searchViewModel.searchedResult.observe(
                    it.viewLifecycleOwner, { contents ->
                        val newContents = filterSeries(contents, filterTypes.get(pageIdx))
                        adapter.submitList(newContents)
                    })
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
                putInt(PAGE_POSITION_BUNDLE_KEY, position)
            }
        }
    }

}


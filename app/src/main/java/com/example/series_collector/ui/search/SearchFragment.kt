package com.example.series_collector.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.series_collector.data.model.Series
import com.example.series_collector.data.repository.CategoryType
import com.example.series_collector.databinding.FragmentDetailSeriesBinding
import com.example.series_collector.databinding.FragmentSearchBinding
import com.example.series_collector.ui.adapters.SearchPagerStateAdapter
import com.example.series_collector.ui.adapters.SearchSeriesAdapter
import com.example.series_collector.ui.adapters.SeriesFollowedAdapter
import com.example.series_collector.ui.adapters.SeriesVideoAdapter
import com.example.series_collector.ui.detail.DetailFragmentArgs
import com.example.series_collector.ui.detail.DetailViewModel
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
                searchViewModel._searchedResult.observe(
                    it.viewLifecycleOwner, { contents ->
                        val newContents = filterSeries(contents, filterTypes.get(pageIdx))
                        adapter.submitList(newContents)
                    })
            }
        }
    }

    private fun filterSeries(tasks: List<Series>, filteringType: SearchFilterType): List<Series> {
        val seriesToShow = ArrayList<Series>()
        for (task in tasks) {
            when (filteringType) {
                SearchFilterType.ALL_FILTER_PAGE -> seriesToShow.add(task)
                SearchFilterType.FICTION_FILTER_PAGE -> if (task.genre == 1) {
                    seriesToShow.add(task)
                }
                SearchFilterType.TRAVEL_FILTER_PAGE -> if (task.genre == 2) {
                    seriesToShow.add(task)
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


package com.example.series_collector.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.series_collector.databinding.FragmentDetailSeriesBinding
import com.example.series_collector.databinding.FragmentSearchBinding
import com.example.series_collector.ui.adapters.SearchPagerStateAdapter
import com.example.series_collector.ui.adapters.SeriesVideoAdapter
import com.example.series_collector.ui.detail.DetailFragmentArgs
import com.example.series_collector.ui.detail.DetailViewModel

private const val SEARCH_PAGE_KEY = "searchKey"

class SearchFragment : Fragment() {

    lateinit private var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val filterTypes = SearchFilterType.values().toList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        fun newInstance(position: Int): SearchFragment = SearchFragment().apply {
            arguments?.putInt(SEARCH_PAGE_KEY, position)
        }
    }

}


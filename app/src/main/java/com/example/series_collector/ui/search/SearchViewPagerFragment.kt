package com.example.series_collector.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.series_collector.R
import com.example.series_collector.databinding.FragmentInventoryBinding
import com.example.series_collector.databinding.FragmentSearchBinding
import com.example.series_collector.databinding.FragmentViewPagerBinding
import com.example.series_collector.ui.Inventory.InventoryItemCallback
import com.example.series_collector.ui.Inventory.InventoryViewModel
import com.example.series_collector.ui.adapters.*
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchViewPagerFragment() : Fragment() {

    private val filterTypes: List<SearchFilterType> = SearchFilterType.values().asList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = SearchPagerStateAdapter(this,filterTypes)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        val filterType = filterTypes.get(position)
        return when (filterType) {
            SearchFilterType.ALL_FILTER_PAGE -> getString(R.string.all_filter_title)
            SearchFilterType.TRAVEL_FILTER_PAGE -> getString(R.string.travel_filter_title)
            SearchFilterType.FICTION_FILTER_PAGE -> getString(R.string.fiction_filter_title)
        }
    }

}



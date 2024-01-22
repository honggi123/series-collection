package com.example.series_collector.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.series_collector.R
import com.example.series_collector.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

private const val NO_PAGE_POSITION_INDEX = 0

@AndroidEntryPoint
class SearchViewPagerFragment() : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val filterTypes = SearchFilterType.values().toList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = SearchPagerStateAdapter(this, filterTypes)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        setUpTabLayout(tabLayout)
        setUpSearchView(binding.searchView)

        return binding.root
    }

    private fun setUpTabLayout(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val filterType = filterTypes.get(tab?.position ?: NO_PAGE_POSITION_INDEX)
                searchViewModel.setFiltering(filterType)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setUpSearchView(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.search(newText.toString())
                return true
            }
        })
    }

    private fun getTabTitle(position: Int): String? {
        val filterType = filterTypes.get(position)
        return when (filterType) {
            SearchFilterType.ALL -> getString(R.string.all_filter_title)
            SearchFilterType.TRAVEL -> getString(R.string.travel_filter_title)
            SearchFilterType.FICTION -> getString(R.string.fiction_filter_title)
        }
    }

}



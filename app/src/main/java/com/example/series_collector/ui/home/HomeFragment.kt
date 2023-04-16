package com.example.series_collector.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.series_collector.databinding.FragmentHomeSeriesListBinding

class HomeFragment : Fragment() {

    lateinit private var binding: FragmentHomeSeriesListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeSeriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

}
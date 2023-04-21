package com.example.series_collector.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import com.example.series_collector.databinding.FragmentHomeSeriesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment() : Fragment() {

    lateinit private var binding: FragmentHomeSeriesListBinding
    private val viewmodel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeSeriesListBinding.inflate(inflater, container, false)

        viewmodel.outputInitWorkInfos.observe(viewLifecycleOwner, workInfosObserver())

        return binding.root
    }


    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (!listOfWorkInfo.isNullOrEmpty()) {

                if (listOfWorkInfo[0].state.isFinished) {
                    binding.isLoading = false
                    return@Observer
                }else{
                    binding.isLoading = true
                }
            }
        }
    }

}
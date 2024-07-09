package com.example.series_collector.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.data.repository.CategoryRepository
import com.example.model.category.CategoryListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    val contentsByCategory: LiveData<List<CategoryListItem>> = categoryRepository.getCategorys()
        .map { it.map { categoryRepository.getCategoryContent(it) } }
        .catch { TODO() }
        .asLiveData()
}
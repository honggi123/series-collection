package com.example.series_collector.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CategoryRepository
import com.example.model.category.CategoryListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _homeContents = MutableLiveData<List<CategoryListItem>>(emptyList())
    val homeContents: LiveData<List<CategoryListItem>> = _homeContents

    init {
        refreshHomeContents()
    }

    private fun refreshHomeContents() {
        viewModelScope.launch {
            _homeContents.value = categoryRepository.getCategorys()
                .map { categoryRepository.getCategoryContent(it) }
        }
    }
}
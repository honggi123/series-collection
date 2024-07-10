package com.example.series_collector.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.data.repository.CategoryRepository
import com.example.model.category.CategoryListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    val _errorMsg = MutableLiveData<String?>(null)
    val errorMsg: LiveData<String?> = _errorMsg

    val contentsByCategory: LiveData<List<CategoryListItem>> = categoryRepository.getCategorys()
        .map {
            it.map { categoryRepository.getCategoryContent(it) }
        }
        .catch {
            when (it) {
                is IOException -> {
                    _errorMsg.value = "네트워크 연결을 확인해주세요."
                }
                else -> {
                    _errorMsg.value = "데이터를 가져오는데 실패했습니다."
                }
            }
        }
        .asLiveData()



}
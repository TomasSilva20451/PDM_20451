package com.example.noticiaAPI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noticiaAPI.domain.repository.NewsRepository
import com.example.noticiaAPI.domain.use_case.GetTopNewsUseCase
import com.example.noticiaAPI.presentation.news_list.NewsViewModel

class NewsViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Create an instance of GetTopNewsUseCase using the repository
        val getTopNewsUseCase = GetTopNewsUseCase(newsRepository)
        // Return the NewsViewModel using the GetTopNewsUseCase
        return NewsViewModel(getTopNewsUseCase) as T
    }
}
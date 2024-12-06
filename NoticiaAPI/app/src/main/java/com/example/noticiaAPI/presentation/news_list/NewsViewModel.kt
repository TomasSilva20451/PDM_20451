package com.example.noticiaAPI.presentation.news_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticiaAPI.data.remote.api.Constant
import com.example.noticiaAPI.data.remote.api.NetworkResponse
import com.example.noticiaAPI.domain.model.News
import com.example.noticiaAPI.domain.use_case.GetTopNewsUseCase
import kotlinx.coroutines.launch


class NewsViewModel(private val getTopNewsUseCase: GetTopNewsUseCase) : ViewModel() {

    private val _newsResult = MutableLiveData<NetworkResponse<List<News>>>()
    val newsResult: LiveData<NetworkResponse<List<News>>> get() = _newsResult

    private var cachedNews: List<News> = emptyList() // Cache the full news list

    init {
        _newsResult.value = NetworkResponse.Success(emptyList())  // Set to empty list initially
    }

    private val supportedLocales = listOf("us", "ca", "br", "pt")
    private val supportedLanguages = listOf("en", "es")


    fun fetchTopNews(
        apiToken: String = Constant.apiKey,
        locale: String = "us",
        language: String = "en",
        headlinesPerCategory: Int = 6
    ) {
        // Check if the locale and language are supported
        if (!supportedLocales.contains(locale)) {
            _newsResult.value = NetworkResponse.Error("Locale not supported")
            return
        }

        if (!supportedLanguages.contains(language)) {
            _newsResult.value = NetworkResponse.Error("Language not supported")
            return
        }

        _newsResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            val result = getTopNewsUseCase.execute(
                apiToken = apiToken,
                locale = locale,
                language = language,
                headlinesPerCategory = headlinesPerCategory
            )
            if (result is NetworkResponse.Success) {
                cachedNews = result.data // Cache the full list of news
            }
            _newsResult.value = result
        }
    }

    fun fetchNewsById(newsId: String) {
        _newsResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                // Fetch directly from cached news

                val filteredNews = cachedNews.find { it.uuid == newsId }
                _newsResult.value = if (filteredNews != null) {
                    NetworkResponse.Success(listOf(filteredNews))
                } else {
                    NetworkResponse.Error("News with ID: $newsId not found.")
                }
            } catch (e: Exception) {
                _newsResult.value = NetworkResponse.Error(e.localizedMessage ?: "Error fetching news.")
            }
        }
    }

    fun getNewsById(newsId: String): News? {
        return cachedNews.find { it.uuid == newsId }
    }
}
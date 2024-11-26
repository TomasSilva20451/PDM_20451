package com.example.noticiaAPI

//  import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticiaAPI.api.Constant
import com.example.noticiaAPI.api.NetworkResponse
import com.example.noticiaAPI.api.NewsItem
import com.example.noticiaAPI.api.RetrofitInstance
import kotlinx.coroutines.launch


class NewsViewModel : ViewModel() {
    private val newsApi = RetrofitInstance.NewsApiApi

    private val _newsResult = MutableLiveData<NetworkResponse<List<NewsItem>>>()
    val newsResult: LiveData<NetworkResponse<List<NewsItem>>> get() = _newsResult

    fun fetchTopNews(
        apiToken: String = Constant.apiKey,
        locale: String = "us",
        language: String = "en",
        headlinesPerCategory: Int = 6
    ) {
        _newsResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = newsApi.getTopNews(
                    apiToken = apiToken,
                    locale = locale,
                    language = language,
                    headlinesPerCategory = headlinesPerCategory
                )
                if (response.isSuccessful) {
                    val articles = response.body()?.data ?: emptyList()
                    _newsResult.value = if (articles.isNotEmpty()) {
                        NetworkResponse.Success(articles)
                    } else {
                        NetworkResponse.Error("No articles found.")
                    }
                } else {
                    _newsResult.value = NetworkResponse.Error("Failed to fetch news: ${response.message()}")
                }
            } catch (e: Exception) {
                _newsResult.value = NetworkResponse.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}
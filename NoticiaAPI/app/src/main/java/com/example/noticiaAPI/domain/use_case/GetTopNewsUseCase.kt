package com.example.noticiaAPI.domain.use_case

import com.example.noticiaAPI.data.remote.api.NetworkResponse
// import com.example.noticiaAPI.data.remote.model.NewsItem
import com.example.noticiaAPI.domain.repository.NewsRepository
import com.example.noticiaAPI.data.remote.api.Constant
import com.example.noticiaAPI.domain.model.News

class GetTopNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(
        apiToken: String = Constant.apiKey,  // API key from the Constant object
        locale: String = "us",
        language: String = "en",
        headlinesPerCategory: Int = 6
    ): NetworkResponse<List<News>> {
        // Call the repository method to get the top news
        return newsRepository.getTopNews(
            apiToken = apiToken,
            locale = locale,
            language = language,
            headlinesPerCategory = headlinesPerCategory
        )
    }
}
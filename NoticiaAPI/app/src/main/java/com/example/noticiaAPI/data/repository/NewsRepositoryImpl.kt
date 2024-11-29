package com.example.noticiaAPI.data.repository

import com.example.noticiaAPI.domain.repository.NewsRepository
import com.example.noticiaAPI.data.remote.api.NetworkResponse
import com.example.noticiaAPI.data.remote.api.NewsApi
import com.example.noticiaAPI.data.remote.api.RetrofitInstance
import com.example.noticiaAPI.data.remote.model.NewsItem
import com.example.noticiaAPI.domain.model.News
// import retrofit2.Response

class NewsRepositoryImpl : NewsRepository {

    private val newsApi: NewsApi = RetrofitInstance.NewsApiApi

    override suspend fun getTopNews(
        apiToken: String,
        locale: String,
        language: String,
        headlinesPerCategory: Int
    ): NetworkResponse<List<News>> {
        return try {
            val response = newsApi.getTopNews(
                apiToken = apiToken,
                locale = locale,
                language = language,
                headlinesPerCategory = headlinesPerCategory
            )

            if (response.isSuccessful) {
                // Map NewsItem to News
                val articles = response.body()?.data?.map { it.toDomainModel() } ?: emptyList()
                NetworkResponse.Success(articles)
            } else {
                NetworkResponse.Error("Failed to fetch news: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Error: ${e.localizedMessage}")
        }
    }
}

// Extension function to map NewsItem (API model) to News (domain model)
fun NewsItem.toDomainModel(): News {
    return News(
        uuid = this.uuid,
        title = this.title,
        description = this.description,
        snippet = this.snippet,
        url = this.url,
        imageUrl = this.image_url,
        language = this.language,
        publishedAt = this.published_at,
        source = this.source,
        categories = this.categories
    )
}
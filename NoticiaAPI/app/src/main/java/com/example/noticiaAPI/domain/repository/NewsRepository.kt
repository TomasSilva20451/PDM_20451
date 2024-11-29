package com.example.noticiaAPI.domain.repository

//import com.example.noticiaAPI.api.NetworkResponse
//import com.example.noticiaAPI.api.NewsItem
import com.example.noticiaAPI.data.remote.api.NetworkResponse
// import com.example.noticiaAPI.data.remote.model.NewsItem
import com.example.noticiaAPI.domain.model.News


interface NewsRepository {
    // The correct method signature should be defined here
    suspend fun getTopNews(
        apiToken: String,
        locale: String,
        language: String,
        headlinesPerCategory: Int
    ): NetworkResponse<List<News>>
}
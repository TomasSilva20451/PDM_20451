package com.example.noticiaAPI.data.remote.api

import com.example.noticiaAPI.data.remote.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v1/news/top")
    suspend fun getTopNews(
        @Query("api_token") apiToken: String,
        @Query("locale") locale: String? = null, // Country filter (e.g., us, ca)
        @Query("language") language: String? = null, // Language filter (e.g., en, es)
        @Query("published_on") publishedOn: String? = null, // Filter for specific dates (e.g., 2024-11-26)
        @Query("headlines_per_category") headlinesPerCategory: Int? = null, // Number of articles per category
        @Query("include_similar") includeSimilar: Boolean? = true // Include similar articles
    ): Response<NewsResponse>
}
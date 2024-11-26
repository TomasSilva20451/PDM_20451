package com.example.noticiaAPI.api

data class NewsResponse(
    val data: List<NewsItem>
)


data class NewsItem(
    val uuid: String,
    val title: String,
    val description: String?,
    val snippet: String?,
    val url: String,
    val image_url: String?,
    val language: String,
    val published_at: String,
    val source: String,
    val categories: List<String> // Add this field to represent the categories array
)
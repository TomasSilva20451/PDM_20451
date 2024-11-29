package com.example.noticiaAPI.data.remote.model

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
    val categories: List<String> // Categories of the news item
)
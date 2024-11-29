package com.example.noticiaAPI.domain.model

data class News(
    val uuid: String,
    val title: String,
    val description: String?,
    val snippet: String?,
    val url: String,
    val imageUrl: String?,
    val language: String,
    val publishedAt: String,
    val source: String,
    val categories: List<String> // Categories of the news item
)
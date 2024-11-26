package com.example.noticiaAPI.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.noticiaAPI.NewsViewModel
import com.example.noticiaAPI.api.Constant
import com.example.noticiaAPI.api.NetworkResponse
import com.example.noticiaAPI.api.NewsItem

@Composable
fun NewsPage(viewModel: NewsViewModel) {
    var selectedLocale by remember { mutableStateOf("us") }
    var selectedLanguage by remember { mutableStateOf("en") }
    val newsResult by viewModel.newsResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Locale and Language Dropdowns
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = selectedLocale,
                onValueChange = { selectedLocale = it },
                label = { Text("Locale (e.g., us, ca)") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = selectedLanguage,
                onValueChange = { selectedLanguage = it },
                label = { Text("Language (e.g., en, es)") }
            )
        }

        // Fetch News Button
        IconButton(onClick = {
            viewModel.fetchTopNews(
                apiToken = Constant.apiKey,
                locale = selectedLocale,
                language = selectedLanguage,
                headlinesPerCategory = 6
            )
        }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Fetch News")
        }

        // News Results
        when (val result = newsResult) {
            is NetworkResponse.Error -> Text(text = result.message, color = Color.Red, fontSize = 16.sp)
            NetworkResponse.Loading -> CircularProgressIndicator()
            is NetworkResponse.Success -> NewsList(news = result.data)
            null -> Text(text = "Start searching for news", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun NewsList(news: List<NewsItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(news.size) { index ->
            val newsItem = news[index]
            NewsItemView(news = newsItem)
        }
    }
}

@Composable
fun NewsItemView(news: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = news.image_url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Source: ${news.source}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Published: ${news.published_at.substring(0, 10)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (news.categories.isNotEmpty()) {
                Text(
                    text = "Categories: ${news.categories.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = news.description ?: "No description available", // Provide a default value
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
package com.example.noticiaAPI.presentation.news_list

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
//import com.example.noticiaAPI.api.NetworkResponse
//import com.example.noticiaAPI.api.NewsItem
import com.example.noticiaAPI.data.remote.api.NetworkResponse
// import com.example.noticiaAPI.data.remote.model.NewsItem
import com.example.noticiaAPI.domain.model.News

@Composable
fun NewsPage(viewModel: NewsViewModel) {
    var selectedLocale by remember { mutableStateOf("us") }  // Allow the user to type freely
    var selectedLanguage by remember { mutableStateOf("en") }
    val newsResult: NetworkResponse<List<News>> by viewModel.newsResult.observeAsState(NetworkResponse.Success(emptyList()))  // Initial empty state
    var localeErrorMessage by remember { mutableStateOf<String?>(null) }  // State to store error message

    // Map of sets of country names to locale codes
    val localeMap = mapOf(
        setOf("United States of America", "united states of america","USA", "usa", "us") to "us",
        setOf("Canada","canada", "CA", "ca") to "ca",
        setOf("Brasil", "Brazil", "brasil", "BR", "br") to "br",
        setOf("Portugal", "portugal", "PT", "pt") to "pt"
    )

    // Convert full country name to corresponding locale code
    val convertCountryNameToLocale = { countryName: String ->
        // Convert input and compare in lowercase for case-insensitive matching
        localeMap.entries.find { entry ->
            entry.key.any { it.trim().equals(countryName.trim(), ignoreCase = true) }
        }?.value
    }

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
                value = selectedLocale,  // Let the user type freely in the locale field
                onValueChange = { selectedLocale = it },  // Just update the value, no conversion here
                label = { Text("Locale (e.g., USA, Canada, Brasil, Portugal)") }
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
            // Convert the locale before making the API call
            val convertedLocale = convertCountryNameToLocale(selectedLocale)

            // If the locale is invalid, show error message
            if (convertedLocale == null) {
                localeErrorMessage = "Invalid Locale! Please use a valid country name (e.g., USA, Canada, Brasil, Portugal)."
            } else {
                localeErrorMessage = null  // Reset error message if locale is valid

                // Trigger the search with the converted locale
                viewModel.fetchTopNews(
                    apiToken = "5QlmEn7Or1ga4YoNJMtQnaFjfjuosgdwD0dmG4DC", // Replace with actual API key
                    locale = convertedLocale,
                    language = selectedLanguage,
                    headlinesPerCategory = 6
                )
            }
        }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Fetch News")
        }

        // Show the error message if the locale is invalid
        localeErrorMessage?.let {
            Text(text = it, color = Color.Red, fontSize = 16.sp)
        }

        // News Results
        when (val result = newsResult) {
            is NetworkResponse.Error -> {
                Text(text = result.message, color = Color.Red, fontSize = 16.sp)
            }
            NetworkResponse.Loading -> CircularProgressIndicator()
            is NetworkResponse.Success -> {
                if (result.data.isEmpty()) {
                    Text(text = "No results found for the selected language/locale.", color = Color.Gray, fontSize = 16.sp)
                } else {
                    NewsList(news = result.data)
                }
            }
        }
    }
}

@Composable
fun NewsList(news:  List<News>) {
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
fun NewsItemView(news: News) {
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
                model = news.imageUrl, // Correct property name
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
                text = "Published: ${news.publishedAt.substring(0, 10)}", // Correct property name
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
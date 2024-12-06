package com.example.noticiaAPI.presentation.news_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.noticiaAPI.domain.model.News
import com.example.noticiaAPI.data.remote.api.NetworkResponse
import com.example.noticiaAPI.presentation.news_list.NewsViewModel

@Composable
fun NewsDetailScreen(navController: NavController, newsId: String, viewModel: NewsViewModel = viewModel()) {
    var newsItem by remember { mutableStateOf<News?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch news details when the screen is displayed
    LaunchedEffect(newsId) {
        viewModel.fetchNewsById(newsId) // Ensure fetchNewsById is implemented in NewsViewModel
    }

    val selectedNews = viewModel.getNewsById(newsId) // Retrieve from cached list

    // Observe the LiveData
    val newsResult by viewModel.newsResult.observeAsState(initial = NetworkResponse.Loading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Handle newsResult with exhaustive 'when' expression
        when (val result = newsResult) {
            is NetworkResponse.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is NetworkResponse.Success -> {
                newsItem = result.data.find { it.uuid == newsId }
                newsItem?.let { news ->
                    Text(news.title, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = news.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Source: ${news.source}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Published: ${news.publishedAt}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(news.description ?: "No description available", style = MaterialTheme.typography.bodyMedium)
                } ?: run {
                    Text("News not found.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.error)
                }
            }
            is NetworkResponse.Error -> {
                errorMessage = result.message
                Text(errorMessage ?: "Error loading news.", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
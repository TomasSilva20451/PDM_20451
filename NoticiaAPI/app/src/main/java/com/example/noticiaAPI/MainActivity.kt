package com.example.noticiaAPI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.noticiaAPI.presentation.news_list.NewsPage
import com.example.noticiaAPI.presentation.news_list.NewsViewModel
import com.example.noticiaAPI.data.repository.NewsRepositoryImpl
import com.example.noticiaAPI.ui.theme.NewsAppTheme // Make sure to import this

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instantiate the repository
        val newsRepository = NewsRepositoryImpl()

        // Create the ViewModelFactory and pass the repository to it
        val newsViewModelFactory = NewsViewModelFactory(newsRepository)

        // Create the ViewModel using the factory
        val newsViewModel = ViewModelProvider(this, newsViewModelFactory)[NewsViewModel::class.java]

        setContent {
            // Apply the custom theme here
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Pass the ViewModel instance correctly to NewsPage
                    NewsPage(viewModel = newsViewModel)
                }
            }
        }
    }
}
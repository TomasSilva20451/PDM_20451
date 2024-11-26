package com.example.noticiaAPI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.noticiaAPI.ui.theme.NewsAppTheme
import com.example.noticiaAPI.ui.theme.NewsPage



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    NewsPage(newsViewModel)
                }
            }
        }
    }
}

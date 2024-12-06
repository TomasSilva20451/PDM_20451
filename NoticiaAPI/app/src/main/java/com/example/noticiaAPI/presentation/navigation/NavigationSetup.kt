package com.example.noticiaAPI.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noticiaAPI.presentation.news_detail.NewsDetailScreen
import com.example.noticiaAPI.presentation.news_list.NewsPage
import com.example.noticiaAPI.presentation.news_list.NewsViewModel

@Composable
fun SetupNavigation(navController: NavHostController, newsViewModel: NewsViewModel) {
    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsPage(viewModel = newsViewModel, navController = navController)
        }
        composable("news_detail/{newsId}") { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId") ?: ""
            NewsDetailScreen(navController = navController, newsId = newsId, viewModel = newsViewModel)
        }
    }
}
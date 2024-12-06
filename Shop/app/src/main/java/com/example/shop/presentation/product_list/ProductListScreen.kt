package com.example.shop.presentation.product_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
//import com.example.shop.presentation.product_list.ProductListViewModel

@Composable
fun ProductListScreen(viewModel: ProductListViewModel) {
    val products = viewModel.products.collectAsState()

    LazyColumn {
        items(products.value) { product ->
            Text(text = product.title)
        }
    }
}
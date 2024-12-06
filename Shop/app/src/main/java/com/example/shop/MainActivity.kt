package com.example.shop


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shop.data.remote.api.FirebaseService
import com.example.shop.data.remote.repository.ProductRepositoryImpl
import com.example.shop.ui.theme.ShopTheme
import com.example.shop.presentation.product_list.ProductListScreen
import com.example.shop.presentation.product_list.ProductListViewModel
import com.example.shop.presentation.product_list.ProductListViewModelFactory
import com.example.shop.presentation.cart.CartScreen
import com.example.shop.presentation.cart.CartViewModel
import com.example.shop.presentation.user.UserScreen
import com.example.shop.presentation.user.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopTheme {

                // Criação do ProductRepositoryImpl com FirebaseService
                val firebaseService = FirebaseService() // Suponha que você tenha uma instância configurada do FirebaseService
                val productRepository = ProductRepositoryImpl(firebaseService)

                // Instancia o ProductListViewModel passando o ProductRepository
                val productViewModel: ProductListViewModel = viewModel(factory = ProductListViewModelFactory(productRepository))

                // Instancia os ViewModels
                val cartViewModel: CartViewModel = viewModel()
                val userViewModel: UserViewModel = viewModel()

                // Chama loadProducts() para carregar os produtos
                productViewModel.loadProducts()

                // Chama o ProductListScreen passando o ProductListViewModel
                ProductListScreen(viewModel = productViewModel)

                // Chama a CartScreen passando o CartViewModel
                CartScreen(viewModel = cartViewModel)

                // Chama a UserScreen passando o UserViewModel
                UserScreen(viewModel = userViewModel)
            }
        }
    }
}
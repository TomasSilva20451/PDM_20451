package com.example.shop.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.model.Product
import com.example.shop.domain.repository.ProductRepository
import com.example.shop.domain.use_case.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(productRepository: ProductRepository) : ViewModel() {

    // Use productRepository directly as a property of the ViewModel
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val getProductsUseCase = GetProductsUseCase(productRepository)

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = getProductsUseCase() // Chama GetProductsUseCase com o repository
        }
    }
}
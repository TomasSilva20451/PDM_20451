package com.example.shop.domain.use_case


import com.example.shop.domain.model.Product
import com.example.shop.domain.repository.ProductRepository


class GetProductsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}

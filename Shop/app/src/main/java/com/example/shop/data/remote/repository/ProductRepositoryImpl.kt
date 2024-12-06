package com.example.shop.data.remote.repository

import com.example.shop.data.remote.api.FirebaseService
import com.example.shop.domain.model.Product
import com.example.shop.domain.repository.ProductRepository

class ProductRepositoryImpl(private val firebaseService: FirebaseService) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return firebaseService.getProducts().map { dto ->
            Product(id = dto.id, title = dto.title, price = dto.price)
        }
    }
}
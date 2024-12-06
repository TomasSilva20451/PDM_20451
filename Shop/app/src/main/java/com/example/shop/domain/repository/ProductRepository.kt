package com.example.shop.domain.repository

import com.example.shop.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}
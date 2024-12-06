package com.example.shop.data.remote.api

import com.example.shop.data.remote.model.ProductDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseService {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getProducts(): List<ProductDto> {
        val snapshot = db.collection("products").get().await()
        return snapshot.documents.map { it.toObject(ProductDto::class.java)!! }
    }
}
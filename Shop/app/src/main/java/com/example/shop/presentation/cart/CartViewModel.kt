package com.example.shop.presentation.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CartItem(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double
)

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Custom operator for adding items
    operator fun plusAssign(item: CartItem) {
        _cartItems.value = _cartItems.value + item
    }

    // Custom operator for removing items
    operator fun minusAssign(item: CartItem) {
        _cartItems.value = _cartItems.value.filter { it.productId != item.productId }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
}
package com.example.shop.presentation.cart


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CartScreen(viewModel: CartViewModel) {
    // Exemplo de produto que você quer adicionar/remover
    val item = CartItem("1", "Product Name", 1, 10.0)

    // Exibir lista de produtos no carrinho
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(viewModel.cartItems.value) { cartItem ->
            CartItemView(cartItem = cartItem)
        }
    }

    // Botões de adicionar/remover
    Row(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { viewModel += item }) {
            Text("Adicionar ao carrinho")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { viewModel -= item }) {
            Text("Remover do carrinho")
        }
    }

    // Exibir total
    val total = viewModel.getTotalPrice()
    Text(
        text = "Total: $total",
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.bodyLarge // Use bodyLarge instead of body1
    )
}

@Composable
fun CartItemView(cartItem: CartItem) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Loading image from a URL using Coil
            AsyncImage(
                model = "https://example.com/path/to/image.jpg", // Replace with actual image URL
                contentDescription = "Product Image",
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = cartItem.productName)
                Text(text = "Quantidade: ${cartItem.quantity}")
                Text(text = "Preço: ${cartItem.price}")
            }

            Text(text = "Total: ${cartItem.price * cartItem.quantity}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCartScreen() {
    val cartViewModel = CartViewModel() // Use a ViewModel instance here
    CartScreen(viewModel = cartViewModel)
}
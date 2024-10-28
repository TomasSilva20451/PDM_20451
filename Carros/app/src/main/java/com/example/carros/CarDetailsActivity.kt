package com.example.carros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

class CarDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recebe o nome do carro a partir da Intent, ou define um valor padrão se nulo
        val carName = intent.getStringExtra("CAR_NAME") ?: "Detalhes do Carro"

        setContent {
            CarDetailsScreen(carName = carName, onBackClick = { finish() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailsScreen(carName: String, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(carName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {  // Botão para voltar
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Modelo: Série 7")
            Text(text = "Segmentação: Cabrio")
            Text(text = "Ano: 2022")
            Text(text = "Combustível: Plug-In")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarDetailsPreview() {
    CarDetailsScreen(carName = "BMW", onBackClick = {})
}
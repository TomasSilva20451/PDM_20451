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
import android.content.Context
import java.io.File

class CarDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val carName = intent.getStringExtra("CAR_NAME") ?: "Detalhes do Carro"

        val carDetails = getCarDetails(this, carName) // Obtenha os detalhes do carro

        setContent {
            CarDetailsScreen(
                carName = carName,
                carDetails = carDetails,
                onBackClick = { finish() }
            )
        }
    }
}

fun getCarDetails(context: Context, carName: String): String {
    val file = File(context.filesDir, "carros.txt")
    if (file.exists()) {
        val lines = file.readLines()
        return lines.find { it.startsWith(carName) } ?: "Detalhes não encontrados"
    }
    return "Arquivo não encontrado"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailsScreen(carName: String, carDetails: String, onBackClick: () -> Unit) {
    // Divida os detalhes do carro com base na vírgula
    val details = carDetails.split(",").map { it.trim() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(carName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Exibe cada detalhe em uma caixa de texto
            if (details.isNotEmpty()) {
                OutlinedTextField(
                    value = details.getOrNull(0) ?: "",
                    onValueChange = {},
                    label = { Text("Marca") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = details.getOrNull(1) ?: "",
                    onValueChange = {},
                    label = { Text("Modelo") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = details.getOrNull(2) ?: "",
                    onValueChange = {},
                    label = { Text("Segmento") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = details.getOrNull(3) ?: "",
                    onValueChange = {},
                    label = { Text("Ano") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = details.getOrNull(4) ?: "",
                    onValueChange = {},
                    label = { Text("Combustível") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarDetailsPreview() {
    CarDetailsScreen(carName = "BMW", carDetails = "Detalhes do carro", onBackClick = {})
}
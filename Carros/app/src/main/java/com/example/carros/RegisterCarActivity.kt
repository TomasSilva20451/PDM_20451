package com.example.carros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment


class RegisterCarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterCarScreen(onRegister = { marca, modelo, segmentacao, ano, combustivel ->
                addCarToDatabase(marca, modelo, segmentacao, ano, combustivel)
                finish() // Fecha a atividade após o registro
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterCarScreen(onRegister: (String, String, String, String, String) -> Unit) {
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var segmentacao by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }
    var combustivel by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Novo Carro") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca") }
            )
            OutlinedTextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") }
            )
            OutlinedTextField(
                value = segmentacao,
                onValueChange = { segmentacao = it },
                label = { Text("Segmentação") }
            )
            OutlinedTextField(
                value = ano,
                onValueChange = { ano = it },
                label = { Text("Ano") }
            )
            OutlinedTextField(
                value = combustivel,
                onValueChange = { combustivel = it },
                label = { Text("Combustível") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onRegister(marca, modelo, segmentacao, ano, combustivel)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Submeter")
            }
        }
    }
}
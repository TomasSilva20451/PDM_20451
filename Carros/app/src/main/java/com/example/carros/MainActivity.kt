package com.example.carros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.activity.result.contract.ActivityResultContracts
import java.io.File



class MainActivity : ComponentActivity() {
    private val requestCodeRegisterCar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Atualiza a lista quando o novo carro é salvo com sucesso
            refreshCarList()
        }
    }

    private val carList = mutableStateListOf<String>() // Lista observável dos carros

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshCarList() // Carrega os carros ao iniciar a atividade

        setContent {
            MainScreen(
                carList = carList,
                onCarClick = { carName ->
                    startActivity(Intent(this, CarDetailsActivity::class.java).apply {
                        putExtra("CAR_NAME", carName)
                    })
                },
                onAddCarClick = { openRegisterCarActivity() }
            )
        }
    }

    private fun openRegisterCarActivity() {
        val intent = Intent(this, RegisterCarActivity::class.java)
        requestCodeRegisterCar.launch(intent) // Usando a nova API de Activity Result
    }

    private fun refreshCarList() {
        carList.clear() // Limpa a lista atual
        carList.addAll(loadCarData(this)) // Recarrega a lista do arquivo `carros.txt`
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    carList: List<String>,
    onCarClick: (String) -> Unit,
    onAddCarClick: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Lista de Carros") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCarClick) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Carro")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(carList.size) { index ->
                CarListItem(carList[index], onCarClick)
            }
        }
    }
}

@Composable
fun CarListItem(carName: String, onCarClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCarClick(carName) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(carName, fontWeight = FontWeight.Bold)
        Icon(Icons.Filled.ArrowForward, contentDescription = "Ver Detalhes")
    }
}

// Função para ler o conteúdo do arquivo "carros.txt"
fun loadCarData(context: Context): List<String> {
    val file = File(context.filesDir, "carros.txt")
    return if (file.exists()) {
        file.readLines().map { line ->
            line.split(",")[0].trim() // Pega apenas a marca (primeiro elemento antes da vírgula)
        }
    } else {
        emptyList()
    }
}


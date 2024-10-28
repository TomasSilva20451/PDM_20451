package com.example.carros

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
//import androidx.compose.material.icons.filled.AutoMirrored
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carros.ui.theme.CarrosTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen { carName ->
                // Navegar para a tela de detalhes
                startActivity(Intent(this, CarDetailsActivity::class.java).apply {
                    putExtra("CAR_NAME", carName)
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onCarClick: (String) -> Unit) {
    val carList = remember { mutableStateListOf("BMW", "Mercedes") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Carros") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { carList.add("Novo Carro ${carList.size + 1}") }
            ) {
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarrosTheme {
        MainScreen { }
    }
}
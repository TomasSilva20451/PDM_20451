package com.example.carros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context
import android.util.Log

// Classe principal para a tela de registro de carro
class RegisterCarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterCarScreen { marca, modelo, segmentacao, ano, combustivel ->
                saveCarToFile(this, marca, modelo, segmentacao, ano, combustivel)
                setResult(RESULT_OK)  // Marca como sucesso para indicar que há uma atualização
                finish()
            }
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
        topBar = { TopAppBar(title = { Text("Novo Carro") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = marca, onValueChange = { marca = it }, label = { Text("Marca") })
            OutlinedTextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") })
            OutlinedTextField(value = segmentacao, onValueChange = { segmentacao = it }, label = { Text("Segmentação") })
            OutlinedTextField(value = ano, onValueChange = { ano = it }, label = { Text("Ano") })
            OutlinedTextField(value = combustivel, onValueChange = { combustivel = it }, label = { Text("Combustível") })

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onRegister(marca, modelo, segmentacao, ano, combustivel)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Salvar")
            }
        }
    }
}

fun saveCarToFile(context: Context, marca: String, modelo: String, segmentacao: String, ano: String, combustivel: String) {
    val carroData = "$marca, $modelo, $segmentacao, $ano, $combustivel\n"
    val fileName = "carros.txt"

    CoroutineScope(Dispatchers.IO).launch {
        context.openFileOutput(fileName, Context.MODE_APPEND).use {
            it.write(carroData.toByteArray())
        }

        // Verifica se o arquivo foi atualizado e loga o conteúdo
        if (checkFileExists(context)) {
            Log.d("CarrosFile", "Arquivo atualizado com conteúdo: ${readFileContents(context)}")
        }
    }
}


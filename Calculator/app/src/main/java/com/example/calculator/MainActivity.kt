package com.example.calculator

import java.util.Locale
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Classe principal da atividade (Activity)
class MainActivity : ComponentActivity() {
    // Função onCreate que define o conteúdo da tela
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Define o tema Material3 para o conteúdo da calculadora
            MaterialTheme {
                CalculatorApp()  // Invoca o composable que desenha a calculadora
            }
        }
    }
}

// Função Composable que desenha a interface da calculadora
@Composable
fun CalculatorApp() {
    // Variáveis de estado para armazenar os números e o operador
    var displayText by remember { mutableStateOf("0") }  // Texto do display
    var firstNumber by remember { mutableStateOf("") }   // Primeiro número
    var operator by remember { mutableStateOf("") }      // Operador (ex: +, -, *)
    var secondNumber by remember { mutableStateOf("") }  // Segundo número
    var calculationComplete by remember { mutableStateOf(false) }  // Flag para indicar se o cálculo foi completado

    // Layout principal da calculadora (coluna)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)  // Espaçamento entre os elementos
    ) {
        // Display da calculadora (exibir o texto da operação ou resultado)
        Text(
            text = displayText,            // Texto exibido no display
            fontSize = 54.sp,              // Tamanho da fonte
            fontWeight = FontWeight.Bold,  // Texto em negrito
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray) // Fundo cinza claro
                .padding(20.dp),            // Espaçamento interno
            maxLines = 1                    // Limitar a 1 linha
        )

        // Coluna para os botões (números e operadores)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),  // Espaçamento entre linhas
            horizontalAlignment = Alignment.CenterHorizontally // Alinhamento horizontal centralizado
        ) {
            // Lista de números para os botões (3x3 números e última linha com C, 0, =)
            val numberButtons = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("C", "0", "=")
            )

            // Lista de operadores
            val operators = listOf("+", "-", "*", "/")

            // Itera sobre as linhas dos botões numéricos e operadores
            numberButtons.forEachIndexed { index, row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),  // Espaçamento entre os botões
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Desenha os botões numéricos na linha
                    row.forEach { label ->
                        CalculatorButton(label) {
                            when (label) {
                                "C" -> {  // Limpa o display e as variáveis
                                    displayText = "0"
                                    firstNumber = ""
                                    secondNumber = ""
                                    operator = ""
                                    calculationComplete = false  // Resetar o estado da calculadora
                                }
                                "=" -> {  // Calcula o resultado
                                    if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && operator.isNotEmpty()) {
                                        val result = calculateResult(firstNumber.toDouble(), secondNumber.toDouble(), operator)
                                        displayText = result.toString()  // Exibe o resultado
                                        firstNumber = result.toString()  // Atualiza o primeiro número para o resultado
                                        secondNumber = ""  // Limpa o segundo número
                                        operator = ""  // Limpa o operador
                                        calculationComplete = true  // Define que o cálculo foi completado
                                    }
                                }
                                else -> {  // Concatena os números no primeiro ou segundo número
                                    if (calculationComplete) {
                                        // Se um cálculo foi completado e o usuário digitar um novo número, reseta tudo
                                        firstNumber = label
                                        secondNumber = ""
                                        operator = ""
                                        displayText = firstNumber
                                        calculationComplete = false  // Reseta a flag
                                    } else {
                                        if (operator.isEmpty()) {
                                            firstNumber += label  // Atualiza o primeiro número
                                            displayText = firstNumber  // Atualiza o display
                                        } else {
                                            secondNumber += label  // Atualiza o segundo número
                                            displayText = secondNumber  // Atualiza o display
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Adicionar operador na última coluna
                    CalculatorButton(operators[index]) {
                        // Se o cálculo foi completado e o usuário escolhe um operador, mantém o resultado como firstNumber
                        if (calculationComplete) {
                            calculationComplete = false  // Reseta o cálculo completo
                        }
                        operator = operators[index]  // Define o operador atual
                    }
                }
            }
        }
    }
}

// Função Composable que desenha cada botão da calculadora
@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,  // Alinha o texto ao centro do botão
        modifier = Modifier
            .size(86.dp)                      // Tamanho do botão (largura e altura)
            .background(Color.LightGray)      // Fundo do botão
            .clickable { onClick() }           // Torna o botão clicável
    ) {
        Text(text = label, fontSize = 30.sp, color = Color.Black)  // Exibe o texto do botão
    }
}

// Função que calcula o resultado da operação
fun calculateResult(firstNumber: Double, secondNumber: Double, operator: String): Double {
    // Realiza a operação com base no operador
    val result = when (operator) {
        "+" -> firstNumber + secondNumber
        "-" -> firstNumber - secondNumber
        "*" -> firstNumber * secondNumber
        "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else 0.0  // Evita divisão por zero
        else -> 0.0  // Caso padrão
    }
    // Limita o resultado a 3 casas decimais usando Locale.US
    return String.format(Locale.US, "%.3f", result).toDouble()
}

// Função de pré-visualização da calculadora no Android Studio
@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    MaterialTheme {
        CalculatorApp()  // Exibe a prévia da interface da calculadora
    }
}
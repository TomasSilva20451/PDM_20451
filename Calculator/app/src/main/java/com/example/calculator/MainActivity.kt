package com.example.calculator


import java.util.Locale
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
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
    var memoryValue by remember { mutableDoubleStateOf(0.0) } // Memória para M+, M-, MRC

    // Layout principal da calculadora (coluna)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF989792))  // Cor de fundo personalizada (#989792)
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
                .background(Color(0xFFBCCEB8))  // Cor de fundo do display personalizada (#bcceb8)
                .padding(20.dp),            // Espaçamento interno
            maxLines = 1                    // Limitar a 1 linha
        )

        // Função que processa a entrada numérica e limita a 7 casas decimais
        fun appendNumber(number: String, targetNumber: String): String {
            return if (targetNumber.contains('.')) {
                val decimalPart = targetNumber.substringAfter('.')
                if (decimalPart.length < 7) {  // Limitar a 7 casas decimais
                    targetNumber + number
                } else {
                    targetNumber  // Não adicionar mais se já tiver 7 casas decimais
                }
            } else {
                targetNumber + number
            }
        }

        // Função para lidar com a entrada do número
        fun handleNumberInput(number: String) {
            if (calculationComplete) {
                // Se um cálculo foi completado, começa um novo número
                firstNumber = number
                displayText = firstNumber
                calculationComplete = false
            } else {
                if (operator.isEmpty()) {
                    firstNumber = appendNumber(number, firstNumber)
                    displayText = firstNumber
                } else {
                    secondNumber = appendNumber(number, secondNumber)
                    displayText = secondNumber
                }
            }
        }

        // Adicionando o Spacer para separar o visor dos botões
        Spacer(modifier = Modifier.height(16.dp))  // Espaçamento vertical de 16.dp entre o visor e os botões

        // Coluna para os botões (números e operadores)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),  // Espaçamento entre linhas
            horizontalAlignment = Alignment.CenterHorizontally // Alinhamento horizontal centralizado
        ) {
            //Linha de botões de memória
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ){
                CalculatorButton("MRC") {
                    displayText = memoryValue.toString() // Recupera o valor da memória
                    firstNumber = memoryValue.toString()
                }
                CalculatorButton("M-") {
                    if (displayText.isNotEmpty()) {
                        memoryValue -= displayText.toDouble() // Subtrai o valor atual da memória
                    }
                }
                CalculatorButton("M+") {
                    if (displayText.isNotEmpty()) {
                        memoryValue += displayText.toDouble() // Adiciona o valor atual à memória
                    }
                }
                CalculatorButton("ON/C") {
                    displayText = "0"
                    firstNumber = ""
                    secondNumber = ""
                    operator = ""
                    calculationComplete = false
                }
            }

            // Linha de botões especial ON/C e CE
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton("√") {
                    // Aplica a raiz quadrada ao número atual e limita a 3 casas decimais
                    if (operator.isEmpty()) {
                        if (firstNumber.isNotEmpty()) {
                            val number = firstNumber.toDouble()
                            if (number >= 0) {
                                firstNumber = String.format(Locale.US, "%.3f", kotlin.math.sqrt(number))
                                displayText = firstNumber
                            } else {
                                displayText = "Erro" // Número negativo
                            }
                        }
                    } else {
                        if (secondNumber.isNotEmpty()) {
                            val number = secondNumber.toDouble()
                            if (number >= 0) {
                                secondNumber = String.format(Locale.US, "%.3f", kotlin.math.sqrt(number))
                                displayText = secondNumber
                            } else {
                                displayText = "Erro" // Número negativo
                            }
                        }
                    }
                }
                CalculatorButton("%") {
                    if (operator.isEmpty()) {
                        // Se não há operador, calcula a porcentagem do primeiro número
                        if (firstNumber.isNotEmpty()) {
                            firstNumber = (firstNumber.toDouble() / 100).toString()
                            displayText = firstNumber
                        }
                    } else {
                        // Se há um operador, calcula a porcentagem relativa ao primeiro número
                        if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                            secondNumber = ((firstNumber.toDouble() * secondNumber.toDouble()) / 100).toString()
                            displayText = secondNumber
                        }
                    }
                }
                CalculatorButton("+/−") {
                    // Alterna o sinal do número atual
                    if (operator.isEmpty()) {
                        // Inverte o sinal do primeiro número
                        if (firstNumber.isNotEmpty()) {
                            firstNumber = if (firstNumber.startsWith("-")) {
                                firstNumber.removePrefix("-")
                            } else {
                                "-$firstNumber"
                            }
                            displayText = firstNumber
                        }
                    } else {
                        // Inverte o sinal do segundo número
                        if (secondNumber.isNotEmpty()) {
                            secondNumber = if (secondNumber.startsWith("-")) {
                                secondNumber.removePrefix("-")
                            } else {
                                "-$secondNumber"
                            }
                            displayText = secondNumber
                        }
                    }
                }
                CalculatorButton("CE") {
                    if(operator.isEmpty()){
                        firstNumber = ""
                        displayText = "0"
                    } else{
                        secondNumber = ""
                        displayText = "0"
                    }
                }
            }

            // Lista de números para os botões (3x3 números e última linha com C, 0, =)
            val numberButtons = listOf(
                listOf("7", "8", "9"),
                listOf("4", "5", "6"),
                listOf("1", "2", "3"),
                listOf("0", ".", "=")
            )

            // Lista de operadores
            val operators = listOf("÷","x","−","+")

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
                                        displayText = formatResult(result)  // Exibe o resultado formatado
                                        firstNumber = result.toString()  // Atualiza o primeiro número para o resultado
                                        secondNumber = ""  // Limpa o segundo número
                                        operator = ""  // Limpa o operador
                                        calculationComplete = true  // Define que o cálculo foi completado
                                    }
                                }
                                "." -> {
                                    // Adiciona um ponto decimal se não houver ainda
                                    if (operator.isEmpty()) {
                                        if (!firstNumber.contains('.')) {
                                            firstNumber += "."
                                            displayText = firstNumber
                                        }
                                    } else {
                                        if (!secondNumber.contains('.')) {
                                            secondNumber += "."
                                            displayText = secondNumber
                                        }
                                    }
                                }
                                else -> handleNumberInput(label) // Chamada à função de inserção de número

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

// Função para determinar a cor de fundo com base no rótulo do botão
fun getButtonBackgroundColor(label: String): Color {
    return when (label) {
        "MRC", "M-", "M+", "√", "%", "+/−", "÷", "x", "−", "+" -> Color(0xFF161B1E)  // Cor preta personalizada (#161b1e)
        "ON/C", "CE" -> Color(0xFFE24B6F)  // Cor personalizada em hexadecimal (#e24b6f)
        else -> Color(0xFF6D7172) // Cor cinza personalizada (#6d7172)
    }
}

// Função Composable que desenha cada botão da calculadora
@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,  // Alinha o texto ao centro do botão
        modifier = Modifier

            .width(76.dp)  // Largura do botão
            .height(60.dp)  // Altura reduzida (você pode ajustar conforme necessário)
            .background(
                color = getButtonBackgroundColor(label),  // Cor de fundo baseada no rótulo
                shape = RoundedCornerShape(15.dp)  // Cantos arredondados com 12.dp
            )
            .clickable { onClick() }           // Torna o botão clicável
    ) {
        Text(text = label, fontSize = 24.sp, color = Color.White)  // Exibe o texto do botão
    }
}

// Função que calcula o resultado da operação com BigDecimal para maior precisão
fun calculateResult(firstNumber: Double, secondNumber: Double, operator: String): Double {
    // Realiza a operação com base no operador
    val result = when (operator) {
        "+" -> firstNumber + secondNumber
        "−" -> firstNumber - secondNumber
        "x" -> firstNumber * secondNumber
        "÷" -> if (secondNumber != 0.0) firstNumber / secondNumber else 0.0  // Evita divisão por zero
        else -> 0.0  // Caso padrão
    }
    return result
}

// Função que formata o resultado para exibir como inteiro ou decimal
fun formatResult(result: Double): String {
    return if (result % 1 == 0.0) {
        // Se for inteiro, converte para um inteiro e exibe sem casas decimais
        result.toInt().toString()
    } else {
        // Se tiver parte decimal, exibe com até 3 casas decimais
        String.format(Locale.US, "%.2f", result)
    }
}

// Função de pré-visualização da calculadora no Android Studio
@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    MaterialTheme {
        CalculatorApp()  // Exibe a prévia da interface da calculadora
    }
}
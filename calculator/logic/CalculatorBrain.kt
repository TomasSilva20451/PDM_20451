package com.example.calculator.logic

import java.util.Locale

// Função que calcula o resultado da operação com base nos números e no operador
fun calculateResult(firstNumber: Double, secondNumber: Double, operator: String): Double {
    // Realiza a operação com base no operador fornecido
    val result = when (operator) {
        "+" -> firstNumber + secondNumber
        "−" -> firstNumber - secondNumber
        "x" -> firstNumber * secondNumber
        "÷" -> if (secondNumber != 0.0) firstNumber / secondNumber else 0.0  // Evita divisão por zero
        else -> 0.0  // Caso padrão, retorna 0.0 se o operador não for reconhecido
    }
    return result
}

// Função que formata o resultado para exibir como inteiro ou decimal com até 3 casas decimais
fun formatResult(result: Double): String {
    return if (result % 1 == 0.0) {
        // Se for um número inteiro, exibe sem casas decimais
        result.toInt().toString()
    } else {
        // Se for um número decimal, formata para exibir até 3 casas decimais
        String.format(Locale.US, "%.3f", result)
    }
}
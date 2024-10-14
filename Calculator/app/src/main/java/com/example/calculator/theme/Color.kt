package com.example.calculator.theme

import androidx.compose.ui.graphics.Color

// Definição de cores específicas usadas na calculadora
val Black = Color(0xFF161B1E)
val Red = Color(0xFFE24B6F)
val Gray = Color(0xFF6D7172)
val ScreenBackground = Color(0xFFBCCEB8)

// Função para determinar a cor de fundo com base no rótulo do botão
fun getButtonBackgroundColor(label: String): Color {
    return when (label) {
        "MRC", "M-", "M+", "√", "%", "+/−", "÷", "x", "−", "+" -> Black  // Cor preta personalizada
        "ON/C", "CE" -> Red  // Cor vermelha personalizada
        else -> Gray  // Cor cinza personalizada
    }
}
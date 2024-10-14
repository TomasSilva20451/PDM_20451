package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.calculator.ui.CalculatorApp
import com.example.calculator.theme.CalculatorTheme

// Classe principal da atividade (Activity)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o tema da calculadora e seta o conteúdo
        setContent {
            CalculatorTheme {
                CalculatorApp()  // Invoca a UI da calculadora
            }
        }
    }
}
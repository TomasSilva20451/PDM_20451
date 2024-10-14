package com.example.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.calculator.theme.getButtonBackgroundColor

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
                shape = RoundedCornerShape(15.dp)  // Cantos arredondados com 15.dp
            )
            .clickable { onClick() }  // Torna o botão clicável
    ) {
        Text(text = label, fontSize = 24.sp, color = Color.White)  // Exibe o texto do botão
    }
}
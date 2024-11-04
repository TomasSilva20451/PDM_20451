package com.example.carros

import android.content.Context
import java.io.File

fun checkFileExists(context: Context): Boolean {
    val file = File(context.filesDir, "carros.txt")
    return file.exists() && file.length() > 0 // Verifica se o arquivo existe e não está vazio
}

fun readFileContents(context: Context): String {
    val file = File(context.filesDir, "carros.txt")
    return if (file.exists()) {
        file.readText()
    } else {
        "Arquivo não encontrado"
    }
}
package com.example.shop.presentation.user

import androidx.lifecycle.ViewModel
import com.example.shop.domain.model.User

class UserViewModel : ViewModel() {
    // Aqui você pode substituir por lógica de carregamento de dados reais
    val userData = User(name = "João Silva", email = "joao.silva@example.com")
}
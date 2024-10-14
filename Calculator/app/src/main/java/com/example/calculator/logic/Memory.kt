package com.example.calculator.logic

class Memory {
    private var memoryValue: Double = 0.0

    fun addToMemory(value: Double) {
        memoryValue += value
    }

    fun subtractFromMemory(value: Double) {
        memoryValue -= value
    }

    fun recallMemory(): Double {
        return memoryValue
    }

    fun clearMemory() {
        memoryValue = 0.0
    }
}
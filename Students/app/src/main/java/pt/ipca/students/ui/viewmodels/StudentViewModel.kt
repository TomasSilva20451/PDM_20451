package pt.ipca.students.ui.viewmodels

import androidx.lifecycle.ViewModel
//import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.ipca.students.data.entity.Student
import pt.ipca.students.data.repository.StudentRepository
//import android.util.Log


class StudentViewModel(private val repository: StudentRepository) : ViewModel() {
    val students = repository.allStudents

    // Função de inserção de estudante
    fun insertStudent(student: Student) {
        viewModelScope.launch {
            repository.insert(student)
        }
    }
}
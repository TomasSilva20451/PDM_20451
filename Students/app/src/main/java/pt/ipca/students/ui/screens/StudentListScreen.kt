package pt.ipca.students.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import pt.ipca.students.ui.viewmodels.StudentViewModel
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
import pt.ipca.students.data.entity.Student
import androidx.compose.ui.unit.dp

@Composable
fun StudentListScreen(studentViewModel: StudentViewModel, modifier: Modifier = Modifier) {
    // Observing the students from the ViewModel using collectAsState with an initial empty list
    val students by studentViewModel.students.collectAsState(initial = emptyList())

    // Log to verify that students are being observed and received in the composable
    Log.d("StudentListScreen", "Observed students: $students")

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = {
                // Adiciona um novo estudante
                studentViewModel.insertStudent(Student(name = "Tomás"))
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Adicionar Estudante")
        }

        LazyColumn {
            items(students) { student ->
                Text(text = student.name)
            }
        }
    }
}
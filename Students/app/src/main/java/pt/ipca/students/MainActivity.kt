package pt.ipca.students

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import pt.ipca.students.data.database.AppDatabase
import pt.ipca.students.data.entity.Student
import pt.ipca.students.data.repository.StudentRepository
import pt.ipca.students.ui.screens.StudentListScreen
import pt.ipca.students.ui.theme.StudentsTheme
import pt.ipca.students.ui.viewmodels.StudentViewModel

class MainActivity : ComponentActivity() {

    private lateinit var studentViewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = StudentRepository(database.studentDao())
        studentViewModel = StudentViewModel(repository)
        enableEdgeToEdge()
        setContent {
            StudentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentListScreen(studentViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
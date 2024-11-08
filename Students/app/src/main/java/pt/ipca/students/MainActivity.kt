package pt.ipca.students

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pt.ipca.students.data.database.AppDatabase
import pt.ipca.students.data.repository.StudentRepository
import pt.ipca.students.ui.screens.StudentListScreen
import pt.ipca.students.ui.theme.StudentsTheme
import pt.ipca.students.ui.viewmodels.StudentViewModel

class MainActivity : ComponentActivity() {

    private lateinit var studentViewModel: StudentViewModel
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura o Firebase Database Reference
        firebaseRef = FirebaseDatabase.getInstance().getReference("test")

        // Configura o banco de dados Room e o ViewModel
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = StudentRepository(database.studentDao())
        studentViewModel = StudentViewModel(repository)

        enableEdgeToEdge()
        setContent {
            StudentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Botão para enviar dados para o Firebase
                        Button(onClick = {
                            sendDataToFirebase()
                        }) {
                            Text("Enviar Dados")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Tela de lista de estudantes
                        StudentListScreen(studentViewModel, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

    private fun sendDataToFirebase() {
        firebaseRef.setValue("Envio de dados sucesso")
            .addOnCompleteListener {
                Toast.makeText(this, "Data stored successfully", Toast.LENGTH_SHORT).show()
            }
    }
}
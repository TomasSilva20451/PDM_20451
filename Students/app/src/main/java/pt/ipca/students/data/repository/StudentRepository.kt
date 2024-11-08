package pt.ipca.students.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import pt.ipca.students.data.dao.StudentDao
import pt.ipca.students.data.entity.Student

class StudentRepository(private val studentDao: StudentDao) {
    val allStudents: Flow<List<Student>> = studentDao.getAllStudents()

    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }
}
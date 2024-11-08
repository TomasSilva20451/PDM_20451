package pt.ipca.students.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pt.ipca.students.data.entity.Student

@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: Student)

    @Query("SELECT * FROM students") // Ensure table name matches the Entity
    fun getAllStudents(): Flow<List<Student>>
}
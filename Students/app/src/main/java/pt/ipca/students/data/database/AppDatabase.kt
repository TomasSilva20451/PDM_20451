package pt.ipca.students.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.ipca.students.data.dao.StudentDao
import pt.ipca.students.data.entity.Student

@Database(entities = [Student::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


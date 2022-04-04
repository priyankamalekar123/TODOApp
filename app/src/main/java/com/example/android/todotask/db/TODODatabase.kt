package com.example.android.todotask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.todotask.models.Subtask
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User

@Database(entities = [User::class, Task::class,Subtask::class], version = 1)
abstract class TODODatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: TODODatabase? = null

        fun getDatabase(context: Context): TODODatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TODODatabase::class.java,
                        "TODOdb"
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }


    }
}
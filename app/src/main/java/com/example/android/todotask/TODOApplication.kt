package com.example.android.todotask

import android.app.Application
import com.example.android.todotask.db.TODODatabase
import com.example.android.todotask.repository.UserRepository


class TODOApplication:Application() {

    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        val database =TODODatabase.getDatabase(applicationContext)
         userRepository = UserRepository(database)
    }
}
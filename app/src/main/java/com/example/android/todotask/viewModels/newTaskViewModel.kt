package com.example.android.todotask.viewModels

import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User
import com.example.android.todotask.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class newTaskViewModel(private val userRepository: UserRepository) : ViewModel() {
    val user1 = MutableLiveData<User>()
    fun addTask(task: Task) {
        GlobalScope.launch {
            userRepository.addTask(task)
        }
    }

    fun getUser(email: String) {
        GlobalScope.launch {
            var user = userRepository.getUser(email)
            user1.postValue(user)
        }
    }
}
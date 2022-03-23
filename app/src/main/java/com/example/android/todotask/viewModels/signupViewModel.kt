package com.example.android.todotask.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todotask.models.User
import com.example.android.todotask.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class signupViewModel(private val userRepository: UserRepository) : ViewModel() {

    var Emails = MutableLiveData<List<String>>()
    fun addUser(user: User) {
        GlobalScope.launch {
            userRepository.addUser(user)
        }
    }

    fun getEmails() {
        GlobalScope.launch {
            val Email = userRepository.getEmails()
            Emails.postValue(Email)

        }
    }


}
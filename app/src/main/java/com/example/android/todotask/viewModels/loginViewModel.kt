package com.example.android.todotask.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todotask.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class loginViewModel(private val userRepository: UserRepository) : ViewModel() {

    var emails = MutableLiveData<List<String>>()
    fun getEmail() {
        GlobalScope.launch {
            val email = userRepository.getEmails()
            emails.postValue(email)

        }
    }

    var passwords = MutableLiveData<List<String>>()
    fun getUserPassword() {
        GlobalScope.launch {
            val user = userRepository.getUserPassword()
            Log.d("usermodel", user.toString())
            passwords.postValue(user)

        }
    }
}
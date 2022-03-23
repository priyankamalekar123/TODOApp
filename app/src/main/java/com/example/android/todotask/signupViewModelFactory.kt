package com.example.android.todotask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.todotask.repository.UserRepository
import com.example.android.todotask.viewModels.signupViewModel

class signupViewModelFactory(private val userRepository: UserRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return signupViewModel(userRepository) as T
    }
}
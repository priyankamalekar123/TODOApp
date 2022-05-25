package com.example.android.todotask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.todotask.viewModels.loginViewModel
import kotlinx.android.synthetic.main.fragment_login.*


    class LoginFragment : Fragment(R.layout.fragment_login) {

        lateinit var loginViewModel1: loginViewModel
    lateinit var user_emails: List<String>
    lateinit var user_passwords: List<String>

    //SharedPreferences
    private val sharedPrefFile = "UserCredential"

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (requireActivity().application as TODOApplication).userRepository
        loginViewModel1 = ViewModelProvider(this, LoginViewModelFactory(repository)).get(loginViewModel::class.java)
            // SharedPreference Instances
       val sharedPreferences:SharedPreferences = this.requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)


        loginViewModel1.getEmail()
        loginViewModel1.emails.observe(viewLifecycleOwner, Observer {
            user_emails = it
        })

        loginViewModel1.getUserPassword()
        loginViewModel1.passwords.observe(viewLifecycleOwner, Observer {
            user_passwords = it
        })

        login.setOnClickListener {

            var Email = user_email.text.toString()
            var Password = user_password.text.toString()

            //SharedPreference editor
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()

            //Stored value of Email and Password in sharedPreferences
            editor.putString("email_key",Email)
            editor.putString("password_key",Password)
            editor.apply()
            editor.commit()

            if (Email.isEmpty() || Password.isEmpty()) {
                Toast.makeText(this.context, "Please fill the credentials", Toast.LENGTH_SHORT)
                    .show()
            } else if (Email in user_emails && Password in user_passwords) {
                findNavController().navigate(R.id.action_loginFragment_to_myTaskFragment)
            } else {
                Toast.makeText(this.context, "User does not Registered..Plz Register First", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
package com.example.android.todotask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.todotask.models.User
import com.example.android.todotask.viewModels.signupViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    lateinit var signupviewModel: signupViewModel
    lateinit var user_emails: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (requireActivity().application as TODOApplication).userRepository
        signupviewModel = ViewModelProvider(this, signupViewModelFactory(repository)).get(signupViewModel::class.java)

        signupviewModel.getEmails()
        signupviewModel.Emails.observe(viewLifecycleOwner, Observer {
            user_emails = it
            Log.d("size of email", it.size.toString())
            for (email in user_emails) {
                Log.d("Emails: ", email)
            }
        })
        signup.setOnClickListener {
            var Name = username.text.toString()
            var Email = email.text.toString()
            var Password = password.text.toString()

            if (user_emails.contains(Email)){
                Toast.makeText(this.context,"User already register",Toast.LENGTH_SHORT).show()
            }

            else if(Name.isEmpty() || Email.isEmpty() || Password.isEmpty())
            {
                Toast.makeText(this.context,"Please fill all fields",Toast.LENGTH_SHORT).show()
            }
            else{
                val user = User(0,Name,Email,Password)
                signupviewModel.addUser(user)
                Toast.makeText(this.context,"User Register Successfully",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.welcomeFragment)
            }
        }
    }
}
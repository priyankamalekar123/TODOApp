package com.example.android.todotask

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User
import com.example.android.todotask.viewModels.newTaskViewModel
import kotlinx.android.synthetic.main.fragment_new_task.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    lateinit var newTaskViewModel1: newTaskViewModel
    val cal = Calendar.getInstance()
    lateinit var status1: String
    lateinit var date_is: String
    val user: ArrayList<User> = ArrayList<User>()

    //SharedPreferences
    private val sharedPrefFile = "UserCredential"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        // SharedPreference Instances
        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        //getSharedPreference value
        val getemail = sharedPreferences.getString("email_key", null)
        val getpassword = sharedPreferences.getString("password_key", null)

        val repository = (activity!!.application as TODOApplication).userRepository
        newTaskViewModel1 = ViewModelProvider(
            this,
            newTaskViewModelFactory(repository)
        ).get(newTaskViewModel::class.java)

        newTaskViewModel1.getUser(getemail!!)
        newTaskViewModel1.user1.observe(this, androidx.lifecycle.Observer {
            var user_with_email = it
            user.add(user_with_email)
            Log.d("User with email is ", it.toString())
            Log.d("id is", user.get(0).id.toString())


            //save task
            save_task.setOnClickListener {
                var Title = title1.text.toString()
                var Description = editTextTextMultiLine.text.toString()
                if (Title.isNotEmpty() && date_is.isNotEmpty() && status1.isNotEmpty() && Description.isNotEmpty()) {
                    val task = Task(0, Title, date_is, status1, Description, user.get(0).id)
                    newTaskViewModel1.addTask(task)
                    Toast.makeText(this.context, "Task have been created", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack(R.id.myTaskFragment, false)

                } else {
                    Toast.makeText(this.context, "Please fill the all fields", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        })


        val status = arrayOf("Pending", "Running", "Done")
        val arrayadapter =
            ArrayAdapter(this.context!!, R.layout.spinner_list, status)
        status_spinner.adapter = arrayadapter

        status_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                status1 = status[position]
                //status_text_view.text = status1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)

                //Set date
                val myDateFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myDateFormat, Locale.US)
                date_is = sdf.format(cal.time)
                date1.text = date_is

            }
        }

        date1.setOnClickListener {
            DatePickerDialog(
                context!!, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
    }

    //Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    //handle Item click of menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //get item id to handle iten click
        if (item.itemId == R.id.loguot) {
            val sharedPreferences: SharedPreferences =
                this.activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(this.context, "User Logout successfully...", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.firstFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}
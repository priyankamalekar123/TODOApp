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
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User
import com.example.android.todotask.viewModels.newTaskViewModel
import kotlinx.android.synthetic.main.fragment_detail_view.*
import kotlinx.android.synthetic.main.fragment_new_task.*
import kotlinx.android.synthetic.main.fragment_new_task.date1
import kotlinx.android.synthetic.main.fragment_new_task.editTextTextMultiLine
import kotlinx.android.synthetic.main.fragment_new_task.status_spinner
import kotlinx.android.synthetic.main.fragment_new_task.title1
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    lateinit var newTaskViewModel1: newTaskViewModel
    val cal = Calendar.getInstance()
    var status1: String? = null
    lateinit var date_is: String
    val user: ArrayList<User> = ArrayList<User>()
    var tasks1: ArrayList<Task> = ArrayList<Task>()

    //SharedPreferences
    private val sharedPrefFile = "UserCredential"

    //navigation argument
    //private val args:NewTaskFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

//        var taskid = args.taskId
//        Log.d("task_id_is", taskid.toString())
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


        //set spinner
        val status = arrayOf("Pending", "Running", "Done")
        val arrayadapter = ArrayAdapter(this.context!!, R.layout.spinner_list, status)
        status_spinner.setAdapter(arrayadapter)

        status_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                status1 = status[p2]
                Log.d("value of p2", p2.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        update_task.visibility = View.GONE
        newTaskViewModel1.getUser(getemail!!)
        newTaskViewModel1.user1.observe(this, androidx.lifecycle.Observer {
            var user_with_email = it
            user.add(user_with_email)
            Log.d("User with email is ", it.toString())
            Log.d("id is", user.get(0).id.toString())

            //save task
            save_task.setOnClickListener {
                Toast.makeText(this.context, status1, Toast.LENGTH_SHORT).show()
                var Title = title1.text.toString()

                var Description = editTextTextMultiLine.text.toString()
                if (Title.isNotEmpty() && date_is.isNotEmpty() && status1!!.isNotEmpty() && Description.isNotEmpty()) {
                    val task = Task(0, Title, date_is, status1!!, Description, user.get(0).id)
                    newTaskViewModel1.addTask(task)
                    Toast.makeText(this.context, "Task have been created", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack(R.id.myTaskFragment, false)

                } else {
                    Toast.makeText(this.context, "Please fill the all fields", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            /////////////......update task.......///////////
            var task_id = arguments!!.getInt("task_id")
            Log.d(" task_id", task_id.toString())
            if (task_id != 0) {
                update_task.visibility = View.VISIBLE
                save_task.visibility = View.GONE

                newTaskViewModel1.getAllTaskwithTaskId(task_id)
                newTaskViewModel1.taskId.observe(this, androidx.lifecycle.Observer {
                    tasks1.clear()
                    tasks1.addAll(it)
                    Log.d("tasks1_size", tasks1.size.toString())
                    Log.d("task_id_data", it.toString())
                    title1.setText(it[0].Title)
                    date1.setText(it[0].Date)
                    editTextTextMultiLine.setText(it[0].Description)


                    //set spinner
                    val status = arrayOf("Pending", "Running", "Done")
                    val arrayadapter = ArrayAdapter(this.context!!, R.layout.spinner_list, status)
                    status_spinner.setAdapter(arrayadapter)

                    status_spinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                status1 = status[p2]
                                Log.d("value of p2", p2.toString())
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }
                    var status_from_task = tasks1.get(0).Status
                    Log.d("status_from_task", status_from_task)
                    var spinner_position = arrayadapter.getPosition(status_from_task)
                    status_spinner.setSelection(spinner_position)

                    //Update task
                    update_task.setOnClickListener {
                        var task = Task(tasks1.get(0).id,
                            title1.text.toString(),
                            date1.text.toString(),
                            //status_text_view1.text.toString(),
                            status_spinner.selectedItem.toString(),
                            editTextTextMultiLine.text.toString(),
                            user.get(0).id
                        )
                        newTaskViewModel1.updateTask(task)
                        Toast.makeText(this.context, "Task Updated successfully....", Toast.LENGTH_SHORT)
                            .show()
                       findNavController().popBackStack(R.id.myTaskFragment,false)
                    }
                })

            }


        })

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)

                //Set date
                val myDateFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myDateFormat, Locale.US)
                date_is = sdf.format(cal.time)
                date1.setText(date_is)
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
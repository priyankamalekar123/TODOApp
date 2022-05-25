package com.example.android.todotask

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User
import com.example.android.todotask.utils.ReminderWorker
import com.example.android.todotask.viewModels.newTaskViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_new_task.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit




class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    lateinit var newTaskViewModel1: newTaskViewModel
    val cal = Calendar.getInstance()
    var status1: String? = null
    lateinit var date_is: String
    val user: ArrayList<User> = ArrayList<User>()
    var tasks1: ArrayList<Task> = ArrayList<Task>()
    lateinit var token:String
    //SharedPreferences
    private val sharedPrefFile = "UserCredential"

    //navigation argument
    //private val args:NewTaskFragmentArgs by navArgs()


    @SuppressLint("LogConditional")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        //firebase token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("token_error", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.d("token",token)
        })


        // 1
        var Year1 = 0
        var Month1 = 0
        var Day1 = 0

//        var taskid = args.taskId
//        Log.d("task_id_is", taskid.toString())
        // SharedPreference Instances
        val sharedPreferences: SharedPreferences =
            this.requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        //getSharedPreference value
        val getemail = sharedPreferences.getString("email_key", null)
        val getpassword = sharedPreferences.getString("password_key", null)

        val repository = (requireActivity().application as TODOApplication).userRepository
        newTaskViewModel1 = ViewModelProvider(
            this,
            newTaskViewModelFactory(repository)
        ).get(newTaskViewModel::class.java)


        //set spinner
        val status = arrayOf("Pending", "Running", "Done")
        val arrayadapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, status)
        status_spinner.setAdapter(arrayadapter)

        status_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                status1 = status[p2]
                Log.d("value of p2", p2.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        update_task.visibility = View.GONE
        newTaskViewModel1.getUser(getemail!!)
        newTaskViewModel1.user1.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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

                    // for notification
                    val todayDateTime = Calendar.getInstance()
                    val delayInSeconds = (cal.timeInMillis/1000L) - (todayDateTime.timeInMillis/1000L)

                    Log.d("todays1",(cal.timeInMillis/1000L).toString())
                    Log.d("todays2",(todayDateTime.timeInMillis/1000L).toString())
                    Log.d("todaysdiff", delayInSeconds.toString())

                    createWorkRequest("hello_Priyanka",delayInSeconds)


                } else {
                    Toast.makeText(this.context, "Please fill the all fields", Toast.LENGTH_SHORT)
                        .show()
                }


            }
            /////////////......update task.......///////////
            var task_id = requireArguments().getInt("task_id")
            Log.d(" task_id", task_id.toString())
            if (task_id != 0) {
                update_task.visibility = View.VISIBLE
                save_task.visibility = View.GONE

                newTaskViewModel1.getAllTaskwithTaskId(task_id)
                newTaskViewModel1.taskId.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    tasks1.clear()
                    tasks1.addAll(it)
                    Log.d("tasks1_size", tasks1.size.toString())
                    Log.d("task_id_data", it.toString())
                    title1.setText(it[0].Title)
                    date1.setText(it[0].Date)
                    editTextTextMultiLine.setText(it[0].Description)


                    //set spinner
                    val status = arrayOf("Pending", "Running", "Done")
                    val arrayadapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, status)
                    status_spinner.setAdapter(arrayadapter)

                    status_spinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long,
                            ) {
                                status1 = status[p2]
                                Log.d("value of p2", p2.toString())
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {

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
            @SuppressLint("LogConditional")
            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)

                Year1 = year
                Month1 = month
                Day1 =  day

                Log.d("date1", Year1.toString())
                Log.d("date1", Month1.toString())
                Log.d("date1", Day1.toString())

                //Set date
                val myDateFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myDateFormat, Locale.US)
                date_is = sdf.format(cal.time)
                date1.setText(date_is)


//            // for notification
//
//                val todayDateTime = Calendar.getInstance()
//
//                val delayInSeconds = (cal.timeInMillis/1000L) - (todayDateTime.timeInMillis/1000L)
//
//                 createWorkRequest("hello_Priyanka",delayInSeconds)


            }
        }

        date1.setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListener,
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

        //get item id to handle item click
        if (item.itemId == R.id.loguot) {
            val sharedPreferences: SharedPreferences =
                this.requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(this.context, "User Logout successfully...", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.firstFragment)
        }

        return super.onOptionsItemSelected(item)
    }

    // Private Function to create the OneTimeWorkRequest
    fun createWorkRequest(message: String, timeDelayInSeconds: Long) {
        val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(workDataOf("title" to "Reminder", "message" to message)).build()

        WorkManager.getInstance(this.requireContext()).enqueue(myWorkRequest)
    }

}
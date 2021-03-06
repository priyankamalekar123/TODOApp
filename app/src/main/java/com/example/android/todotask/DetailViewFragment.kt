package com.example.android.todotask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User
import com.example.android.todotask.viewModels.detailViewModel
import kotlinx.android.synthetic.main.fragment_detail_view.*
import kotlinx.android.synthetic.main.fragment_detail_view.title
import kotlinx.android.synthetic.main.fragment_detail_view.view.*
import kotlinx.android.synthetic.main.item_view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailViewFragment() : Fragment(R.layout.fragment_detail_view) {

    lateinit var detailViewModel1: detailViewModel
    var task: ArrayList<Task> = ArrayList<Task>()
    val cal = Calendar.getInstance()
    val task_add: ArrayList<Task> = ArrayList<Task>()
    val user_for_id: ArrayList<User> = ArrayList<User>()
    private val sharedPrefFile = "UserCredential"




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)


        // SharedPreference Instances
        val sharedPreferences: SharedPreferences =
            this.requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        //getSharedPreference value
        val getemail = sharedPreferences.getString("email_key", null)
        val getpassword = sharedPreferences.getString("password_key", null)


        val repository = (requireActivity().application as TODOApplication).userRepository
        detailViewModel1 = ViewModelProvider(
            this,
            detailViewModelFactory(repository)
        ).get(detailViewModel::class.java)

        val position = requireArguments().getInt("position")
        Log.d("position_is ", position.toString())



        update.visibility = View.GONE

        edit.setOnClickListener {
            edit.visibility = View.GONE
            update.visibility = View.VISIBLE

            title.isEnabled = true
            date1.isEnabled = true
            status_spinner.isEnabled = true
            EditTextTextMultiLine.isEnabled = true
        }


        detailViewModel1.getUser(getemail!!)
        detailViewModel1.user1.observe(viewLifecycleOwner, Observer {
            var user = it
            user_for_id.add(user)

            detailViewModel1.getAllTask(it.id)
            detailViewModel1.tasks.observe(viewLifecycleOwner, Observer {
                task = it as ArrayList<Task>
                task.size
                Log.d("tasks_in_function", task.toString())
                // Log.d("size of task list is", task.size.toString())
                task_add.clear()
                task_add.add(task[position])

                Log.d("size of task_add in  ", task_add.size.toString())

                //set title
                title1.setText(task_add.get(0).Title)

                //set date
                date1.setText(task_add.get(0).Date)

                //set description
                editTextTextMultiLine.setText(task_add.get(0).Description)


                //Update task
                update.setOnClickListener {
                    var task = Task(task_add.get(0).id,
                        title1.text.toString(),
                        date1.text.toString(),
                        //status_text_view1.text.toString(),
                        status_spinner.selectedItem.toString(),
                        editTextTextMultiLine.text.toString(),
                        user_for_id.get(0).id
                    )
                    detailViewModel1.updateTask(task)
                    Toast.makeText(this.context, "Task Updated successfully....", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                }


                //function for getting single task
                detailViewModel1.getTask(task_add.get(0).Title)
                detailViewModel1.task1.observe(viewLifecycleOwner, Observer {
                    var single_task = it
                    Log.d("single_task_is = ", single_task.toString())


                    //for status spinner
                    val status = arrayOf("Pending", "Running", "Done")
                    val arrayadapter = ArrayAdapter(this.requireContext(), R.layout.spinner_list, status)
                    status_spinner.setAdapter(arrayadapter)

                    //set status spinner
                    var status_from_task = task_add.get(0).Status
                    var spinner_position = arrayadapter.getPosition(status_from_task)
                    status_spinner.setSelection(spinner_position)

                      status_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                           override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position1: Int, p3: Long) {
                               var status1 = status[position1]
                               Log.d("status is ", status1)

                           }
                          override fun onNothingSelected(p0: AdapterView<*>?) {
                           }
                       }

                })
            })
        })

        //date
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)

                //Set date
                val myDateFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myDateFormat, Locale.US)
                val date_is = sdf.format(cal.time)
                date1.setText(date_is)
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


        //Delete task
//        delete.setOnClickListener {
//            detailViewModel1.deleteTask(task_add.get(0))
//            findNavController().navigate(R.id.action_detailViewFragment_to_myTaskFragment)
//            Toast.makeText(this.context, "Task Deleted successfully....", Toast.LENGTH_SHORT).show()
//        }


        delete.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            //set title for alert dialog
            builder.setTitle("Delete Task")
            //set message for alert dialog
            builder.setMessage("Task delete from your data")

            //performing positive action
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                detailViewModel1.deleteTask(task_add.get(0))
                Toast.makeText(this.context,"your task is deleted",Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->
            }

            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()

            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
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
}
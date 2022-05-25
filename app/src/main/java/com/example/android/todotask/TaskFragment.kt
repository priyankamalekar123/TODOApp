package com.example.android.todotask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.todotask.models.User
import com.example.android.todotask.viewModels.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_task.title
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "position"
private const val ARG_PARAM2 = "id"


class TaskFragment: Fragment(R.layout.fragment_task) {

    private var position1:Int = 0
    private var id1:Int = 0
    lateinit var taskViewModel1: TaskViewModel
    val user_for_id: ArrayList<User> = ArrayList<User>()
    //val task_add: ArrayList<Task> = ArrayList<Task>()
    private val sharedPrefFile = "UserCredential"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().let {
            position1 = it.getInt(ARG_PARAM1)
            id1 = it.getInt(ARG_PARAM2)
        }
        Log.d("p1", position1.toString())
        Log.d("id", id1.toString())


        setHasOptionsMenu(true)

        // SharedPreference Instances
        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        //getSharedPreference value
        val getemail = sharedPreferences.getString("email_key", null)

        val repository = (activity!!.application as TODOApplication).userRepository
        taskViewModel1 = ViewModelProvider(
            this,
            TaskViewModelFactory(repository)
        ).get(TaskViewModel::class.java)
////////////////////////////////////////////////////////////
//        val position1 = arguments!!.getInt("position")
//        Log.d("position_is ", position1.toString())
//
//        val id = arguments!!.getInt("ID")
//        Log.d("iddddd_isssss", id.toString())

        taskViewModel1.getUser(getemail!!)
        taskViewModel1.user1.observe(this, Observer {

            var user = it
            user_for_id.add(user)

            taskViewModel1.getAllTaskwithTaskId(id1)
            taskViewModel1.tasks3.observe(this, Observer {
                var task = it

//                task_add.clear()
//                task_add.add(task)

                //               Log.d("task_is", task.toString())

                //edit_button
                //var task_id =task_add.get(0).id
                var task_id =task.id
                Log.d("task_id", task_id.toString())

                mark_as_completed.setOnClickListener {
                    //Update status
                    taskViewModel1.updateStatus("Done",task_id)
                    Toast.makeText(this.context, "Task Updated successfully....", Toast.LENGTH_SHORT)
                        .show()
                    status_btn.setBackgroundColor(ContextCompat.getColor(this.context!!,R.color.green))
                    status_btn.setText("Task Done")
                    mark_as_completed.visibility = View.GONE
                    //findNavController().popBackStack(R.id.myTaskFragment,false)


                }
                edit.setOnClickListener {
                    var bundle = Bundle()
                    bundle.putInt("task_id",task_id)
                     findNavController().navigate(R.id.action_tabFragment_to_newTaskFragment,bundle)
                }

                //set title
                title.setText(task.Title)

                //set date
                var dateis = task.Date
                    var sdf:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                    var date1:Date = sdf.parse(dateis)
                       sdf.applyPattern("EEE, d MMM yyyy")
                       var str:String = sdf.format(date1)


                date.setText(str)

                //set description
                description.setText(task.Description)

                var status = task.Status

                if(status.equals("Pending")) {
                    status_btn.setBackgroundColor(ContextCompat.getColor(this.context!!,R.color.red))
                    status_btn.setText("Task Pending")
                }
                else if(status.equals("Running")){
                    status_btn.setBackgroundColor(ContextCompat.getColor(this.context!!,R.color.oragne))
                    status_btn.setText("Task Running")
                }
                else{
                    status_btn.setBackgroundColor(ContextCompat.getColor(this.context!!,R.color.green))
                    status_btn.setText("Task Done")
                    mark_as_completed.visibility = View.GONE
                }


            })
        })
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

    companion object{
        fun newInstance(position: Int, id: Int) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, position)
                    putInt(ARG_PARAM2, id)
                }
            }
    }
    }








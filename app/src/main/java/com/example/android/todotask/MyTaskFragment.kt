package com.example.android.todotask

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.todotask.models.Task
import com.example.android.todotask.viewModels.myTaskViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.fragment_my_task.*

import java.text.FieldPosition


class MyTaskFragment : Fragment(R.layout.fragment_my_task), userInterface {

    lateinit var adapter1: MyAdapter
    lateinit var myTaskViewModel1: myTaskViewModel
    var tasks: ArrayList<Task> = ArrayList<Task>()
    var tasksAll:ArrayList<Task> = ArrayList<Task>()
    private val sharedPrefFile = "UserCredential"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)


        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        //getSharedPreference value
        val getemail = sharedPreferences.getString("email_key", null)
        val getpassword = sharedPreferences.getString("password_key", null)


        val repository1 = (activity!!.application as TODOApplication).userRepository
        myTaskViewModel1 = ViewModelProvider(
            this,
            mytaskViewModelFactory(repository1)
        ).get(myTaskViewModel::class.java)

       //////Chip/////
        var chip = Chip(this.context)
        var drawable = ChipDrawable.createFromAttributes(this.requireContext(),null,0,R.style.Widget_MaterialComponents_Chip_Choice)
         chip.setChipDrawable(drawable)
         chipGroup.addView(chip)


        myTaskViewModel1.getUser(getemail!!)
        myTaskViewModel1.user1.observe(this, Observer {
            var user = it
//            Log.d("loged user is", it.toString())
//            Log.d("loged user id is", it.id.toString())


            myTaskViewModel1.getAllTask(user.id)
            myTaskViewModel1.tasks.observe(this, Observer {
                tasks.clear()
                tasks.addAll(it)
                //tasks = it as ArrayList<Task>
                tasksAll= it as ArrayList<Task>
                //dynamic list
                    recyclerview.layoutManager = LinearLayoutManager(this.context)
                    adapter1 = MyAdapter(context!!, tasks, this)
                    //recyclerview.adapter = adapter1
                    recyclerview.adapter = adapter1
                    Log.d("size_of_task_before", tasks.size.toString())
            })
        })

        //for all task
        allTask.setOnClickListener {
            tasks.clear()
            tasks.addAll(tasksAll)
            adapter1.notifyDataSetChanged()

            var chip = Chip(this.context)
            var drawable:ChipDrawable = ChipDrawable.createFromAttributes(this.context!!,null,0,R.style.Widget_MaterialComponents_Chip_Choice)
            chip.setChipDrawable(drawable)
        }

        //for task pending
        allPending.setOnClickListener {
            myTaskViewModel1.getTaskStatus("Pending")
            myTaskViewModel1.taskStatus.observe(this, Observer {
                tasks.clear()
                tasks.addAll(it)
                Log.d("Pending", tasks.toString())
                adapter1.notifyDataSetChanged()

            })
        }
        //for task completed
        allCompleted.setOnClickListener {
            myTaskViewModel1.getTaskStatus("Done")
            myTaskViewModel1.taskStatus.observe(this, Observer {
                tasks.clear()
                tasks.addAll(it)
                Log.d("Pending", tasks.toString())
                adapter1.notifyDataSetChanged()

            })
        }

        floatingbtn.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("task",0)
            findNavController().navigate(R.id.action_myTaskFragment_to_newTaskFragment,bundle)
        }

    }

    override fun onItemClick(position: Int) {
        val task1 = tasks[position]
        val id = tasks[position].id
        val bundle = Bundle()
        bundle.putInt("position", position)
        bundle.putInt("ID",id)
        findNavController().navigate(R.id.action_myTaskFragment_to_tabFragment, bundle)
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
            //findNavController().popBackStack(R.id.firstFragment,false)
            findNavController().navigate(R.id.action_myTaskFragment_to_firstFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}
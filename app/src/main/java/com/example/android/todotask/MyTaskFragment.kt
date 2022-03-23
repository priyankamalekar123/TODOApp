package com.example.android.todotask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.todotask.models.Task
import com.example.android.todotask.viewModels.myTaskViewModel
import kotlinx.android.synthetic.main.fragment_my_task.*

import java.text.FieldPosition


class MyTaskFragment : Fragment(R.layout.fragment_my_task), userInterface {

    lateinit var adapter1: MyAdapter
    lateinit var myTaskViewModel1: myTaskViewModel
    lateinit var tasks: List<Task>
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

        myTaskViewModel1.getUser(getemail!!)
        myTaskViewModel1.user1.observe(this, Observer {
            Log.d("loged user is", it.toString())
            Log.d("loged user id is", it.id.toString())


            myTaskViewModel1.getAllTask(it.id)
            myTaskViewModel1.tasks.observe(this, Observer {

                tasks = it

                recyclerview.layoutManager = LinearLayoutManager(this.context)
                adapter1 = MyAdapter(context!!, tasks, this)
                //recyclerview.adapter = adapter1
                recyclerview.adapter = adapter1


            })
        })

        floatingbtn.setOnClickListener {
            findNavController().navigate(R.id.action_myTaskFragment_to_newTaskFragment)
        }

    }

    override fun onItemClick(position: Int) {
        val task1 = tasks[position]
        val bundle = Bundle()
        bundle.putInt("position", position)
        findNavController().navigate(R.id.action_myTaskFragment_to_detailViewFragment, bundle)
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
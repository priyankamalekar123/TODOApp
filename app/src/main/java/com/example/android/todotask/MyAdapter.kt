package com.example.android.todotask

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todotask.models.Task

class MyAdapter(val context: Context,val task:List<Task>,val userInterface: userInterface):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


 lateinit var myAdapter:MyAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view =inflater.inflate(R.layout.item_view,parent,false)
        return MyViewHolder(view,userInterface)

    }

    val status_task = arrayOf("Pending", "Running", "Done")


    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

       val taskPosition = task[position]
        holder.title.text = taskPosition.Title
        holder.date.text = taskPosition.Date
        holder.status.text = taskPosition.Status
        holder.description.text = taskPosition.Description

        holder.title.setBackgroundResource(R.drawable.title)

        if(taskPosition.Status.equals("Pending")){
            holder.status.setBackgroundResource(R.drawable.status_red)
        }
        else if (taskPosition.Status.equals("Running")){
            holder.status.setBackgroundResource(R.drawable.status_yellow)
        }
        else{
            holder.status.setBackgroundResource(R.drawable.status_green)
        }
    }
    override fun getItemCount(): Int {
       return task.size
    }

    class MyViewHolder(itemview: View, userInterface: userInterface):RecyclerView.ViewHolder(itemview) {

        var title = itemview.findViewById<TextView>(R.id.title)
        var date = itemview.findViewById<TextView>(R.id.date)
        var status = itemview.findViewById<TextView>(R.id.status)
        var description = itemview.findViewById<TextView>(R.id.description)


        init {
            itemview.setOnClickListener {
                userInterface.onItemClick(adapterPosition)

            }

        }

    }



}

